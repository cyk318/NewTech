package org.cyk.ktearth.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

enum class LimitType(
    id: String, // 限流类型唯一标识
    maxReqCnt: Int, // 单位时间内，最大请求数
    timeout: Long, // 超时时间
    unit: TimeUnit // 超时时间单位
) {
    //TODO 文章发布
//    view()
}


interface FlowLimitForRedisService {
//    fun entry(): Boolean
}

@Service
class FlowLimitForRedisServiceImpl: FlowLimitForRedisService {



}