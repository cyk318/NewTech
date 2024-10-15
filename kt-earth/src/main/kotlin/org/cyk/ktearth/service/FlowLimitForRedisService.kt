package org.cyk.ktearth.service

import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.exception.FlowLimitException
import org.cyk.ktearth.infra.model.ApiStatus
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * 限流设计:
 * 描述: 对所有非管理员的写接口进行限流
 * 技术: 使用 redis 实现限流  key:{ LimitType.id + ":" + 用户id }  value: 操作次数
 * Ps: 由于不开放注册接口，因此使用户id一定不为空
 */

enum class LimitType(
    val id: String, // 限流类型唯一标识
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
}

interface FlowLimitForRedisService {
    fun entry(userId: String, type: LimitType)
}

@Service
class FlowLimitForRedisServiceImpl(
    private val redisTemplate: StringRedisTemplate,
): FlowLimitForRedisService {

    //TODO 设计有问题，对不同文章也会有限制
    override fun entry(userId: String, type: LimitType) {
        val key = type.id + ":" + userId
        val beforeCnt = redisTemplate.opsForValue().get(key)

        if (beforeCnt == null) { //1.key 不存在
            // 1) 设置 value，然后返回
            redisTemplate.opsForValue().set(key, "1", type.timeout, type.unit)
            return
        }

        //2.key 存在
        //1) value + 1
        val afterCnt = redisTemplate.opsForValue().increment(key)!!
        //2) 检查没有超过 maxReqCnt，返回 true
        if (afterCnt <= type.maxReqCnt) {
            return
        }
        //3) 超过 maxReqCnt, 检查如果是最大次数的 10 倍，那么就 warn 日志，最后返回 false
        if (afterCnt > type.maxReqCnt * 10) {
            log.error("目标被限流，且疑似有刷接口嫌疑  redisKey: $key ")
        }
        throw FlowLimitException(log = "目标被限流  redisKey: $key")
    }

    companion object {
        private val log = LoggerFactory.getLogger(FlowLimitForRedisServiceImpl::class.java)
    }

}

