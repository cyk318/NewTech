package org.cyk.ktearth.service

import org.cyk.ktearth.infra.exception.FlowLimitException
import org.slf4j.LoggerFactory
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.Date
import java.util.concurrent.TimeUnit

@Document("flow_limit")
data class FlowLimitDo (
    @Id
    val id: String?,
    val type: String,
    val postId: String,
    val targetId: String,
    val cnt: Int,
    var cTime: Long,
)

data class FlowLimit (
    var id: String? = null,
    val type: LimitType,
    val postId: String,
    val targetId: String,
    var cnt: Int = 1,  // 请求次数
    var cTime: Date = Date(),
) {

    fun cntOk(): Boolean {
        return  cnt <= type.maxReqCnt
    }

    fun reset() {
        this.cnt = 1
        this.cTime = Date()
    }

    fun incrCnt() {
        this.cnt++
    }

    fun isExpired(): Boolean {
        return (type.unit.toMillis(type.timeout) + cTime.time) < Date().time
    }

    fun isHighCnt(): Boolean {
        return this.cnt >= this.type.maxReqCnt * 10
    }

}

enum class LimitType(
    val type: String, // 限流类型唯一标识
    val maxReqCnt: Int, // 单位时间内，最大请求数
    val timeout: Long, // 超时时间
    val unit: TimeUnit // 超时时间单位
) {
    //TODO 文章发布
    /**
     * 访问量增加: 一分钟内最多 +1
     */
    VIEW_POST("view_post", 1, 1, TimeUnit.MINUTES),
    ;

    companion object {
        fun typeOf(type: String) = entries.firstOrNull { it.type == type }
    }
}

interface FlowLimitRepo {
    fun save(obj: FlowLimit): FlowLimit
    fun query(type: String, postId: String, targetId: String): FlowLimit?
}

@Repository
class FlowLimitRepoImpl (
    private val mongoTemplate: MongoTemplate,
): FlowLimitRepo {

    override fun save(obj: FlowLimit): FlowLimit {
        val o = map(obj)
        mongoTemplate.save(o).let { obj.id = it.id }
        return obj
    }

    override fun query(type: String, postId: String, targetId: String): FlowLimit? {
        val c = Criteria().apply {
            and("type").`is`(type)
            and("post_id").`is`(postId)
            and("target_id").`is`(targetId)
        }
        return mongoTemplate.findOne(Query.query(c), FlowLimitDo::class.java)?.let { map(it) }
    }

    private fun map(obj: FlowLimitDo): FlowLimit = with(obj) {
        FlowLimit(
            id = id,
            type = LimitType.typeOf(type)!!,
            postId = postId,
            targetId = targetId,
            cnt = cnt,
            cTime = Date(cTime),
        )
    }

    private fun map(obj: FlowLimit): FlowLimitDo = with(obj) {
        FlowLimitDo(
            id = id,
            type = type.type,
            postId = postId,
            targetId = targetId,
            cnt = cnt,
            cTime = cTime.time,
        )
    }

}

interface FlowLimitService {
    fun entry(flowLimit: FlowLimit)
}

@Service
class FlowLimitServiceImpl(
    private val flowLimitRepo: FlowLimitRepo,
): FlowLimitService {

    override fun entry(flowLimit: FlowLimit) {
        val obj = flowLimitRepo.query(flowLimit.type.type, flowLimit.postId, flowLimit.targetId)
        // 不存在就创建
        if (obj == null) {
            flowLimitRepo.save(flowLimit)
            return
        }
        // 如果过期，就设置新的
        if (obj.isExpired()) {
            obj.reset()
            flowLimitRepo.save(obj)
            return
        }

        // 请求次数 +1
        obj.incrCnt()
        // 没有超过最大请求数，直接放行通过
        if (obj.cntOk()) {
            flowLimitRepo.save(obj)
            return
        }
        // 超过最大请求数的 10 倍，记录错误日志
        if (obj.isHighCnt()) {
            log.error("刷接口嫌疑...  postId: ${obj.postId}, targetId: ${obj.targetId}, cnt: ${obj.cnt}, flow limit: ${obj.type}")
        }

        flowLimitRepo.save(obj)
        throw FlowLimitException(log = "请求次数超过上限  postId: ${obj.postId}," +
                " targetId: ${obj.targetId}, cnt: ${obj.cnt}, flow limit: ${obj.type}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(FlowLimitServiceImpl::class.java)
    }

}

