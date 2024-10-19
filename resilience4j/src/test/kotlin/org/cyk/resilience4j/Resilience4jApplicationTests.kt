package org.cyk.resilience4j

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterConfig
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.retry.RetryRegistry
import org.junit.jupiter.api.Test
import java.lang.RuntimeException
import java.time.Duration

class Resilience4jApplicationTests {

    /**
     * 限流器
     */
    @Test
    fun testRareLimiter() {
        // 配置限流器
        val config = RateLimiterConfig.custom()
            .limitForPeriod(2) // 单位时间内最多请求次数
            .limitRefreshPeriod(Duration.ofSeconds(1)) // 时间
            .timeoutDuration(Duration.ofSeconds(1000)) // 超时时间: 超出时间没有响应，就报错 (可选)
            .build()

        // 创建限流器
        val rareLimit = RateLimiter.of("my", config)

        // 模拟 10 次连续请求
        var cnt = 10
        while (cnt-- > 0) {
            // 使用限流器保护代码
            RateLimiter.decorateRunnable(rareLimit) {
                println("业务处理...")
            }.run()
        }
    }

    /**
     * 熔断器
     */
    @Test
    fun testCircuitBreaker() {
        // 配置熔断器
        val config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50f)  // 失败率到达 50% 时打开熔断器
            .waitDurationInOpenState(Duration.ofSeconds(5)) // 熔断器打开 5 秒(可选)
            .slidingWindowSize(10) // 滑动窗口大小为 10 个请求
            .build()

        // 创建熔断器
        val circuitBreaker = CircuitBreaker.of("my", config)

        // 使用熔断器
        // 这里引发异常后会记录
        // 熔断器会统计每 10 个请求里，一旦失败率到达 50%，熔断器就会打开 5s(这个期间的请求都会失败)
        CircuitBreaker.decorateSupplier(circuitBreaker) {
            throw RuntimeException("业务处理失败...")
        }.get()
    }

    @Test
    fun retry() {
        // 配置重试
        val config = RetryConfig.custom<Int>()
            .maxAttempts(3) // 最大重试次数
            .waitDuration(Duration.ofMillis(500)) // 每次重试之间的等待时间
            .build()

        // 创建重试注册表
        val retryReg = RetryRegistry.of(config)
        val retry = retryReg.retry("my")

        //使用
        Retry.decorateRunnable(retry) {
            println("开始业务处理 ...")
            throw RuntimeException("业务处理失败")
        }.run()
    }
}