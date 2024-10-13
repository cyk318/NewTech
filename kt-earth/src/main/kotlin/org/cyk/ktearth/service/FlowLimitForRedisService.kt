package org.cyk.ktearth.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * 限流设计:
 * 描述: 对所有非管理员的写接口进行限流
 * 技术: 使用 redis 实现限流  key:{ LimitType.id + 用户id }  value: 操作次数
 * Ps: 由于不开放注册接口，因此使用户id一定不为空
 */

enum class LimitType(
    id: String, // 限流类型唯一标识
    maxReqCnt: Int, // 单位时间内，最大请求数
    timeout: Long, // 超时时间
    unit: TimeUnit // 超时时间单位
) {
    //TODO 文章发布
    /**
     * 访问量增加: 一分钟内最多 +1
     */
    VIEW_POST("view_post", 1, 1, TimeUnit.MINUTES)
}

interface FlowLimitForRedisService {
    fun entry(userId: String, type: LimitType): Boolean
}

@Service
class FlowLimitForRedisServiceImpl(
    private val redisTemplate: StringRedisTemplate,
): FlowLimitForRedisService {

    override fun entry(userId: String, type: LimitType): Boolean {
        TODO()
        //1.key 不存在
        // 1) 设置 value，然后返回 true
        //2.key 存在
        // 1) value + 1
        // 2) 检查没有超过 maxReqCnt，返回 true
        // 3) 超过 maxReqCnt, 检查如果是最大次数的 10 倍，那么就 warn 日志，最后返回 false
    }

}

