package org.cyk.springcloudstreammq

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import kotlin.random.Random

@SpringBootApplication
class SpringCloudStreamMqApplication {
    private val random = Random(System.currentTimeMillis())
    private val data = arrayOf(
        "abc1", "abc2", "abc3",
        "abc4",
    )

    /**
     * 分区消息:
     * 方法返回一个函数，这个函数每次调用都会从 data 中随机选择一个字符串，
     * 生成一个带有分区键(partitionKey)的消息，并将这个消息返回.
     */
    @Bean
    fun <T> generate(): () -> Message<String> {
        return {
            val value = data[random.nextInt(data.size)]
            println("Sending: $value")
            MessageBuilder.withPayload(value)
                .setHeader("partitionKey", value)
                .build()
        }
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(SpringCloudStreamMqApplication::class.java)
        .web(WebApplicationType.NONE) //不启动 web 相关的组件
        .run(*args)
}