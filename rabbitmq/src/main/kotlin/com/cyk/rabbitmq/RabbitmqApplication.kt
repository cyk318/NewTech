package com.cyk.rabbitmq

import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
class RabbitmqApplication

fun main(args: Array<String>) {
    runApplication<RabbitmqApplication>(*args)
}


@Configuration
class GlobalConfig {

    @Bean("transRabbitTemplate")
    fun transRabbitTemplate(
        connectionFactory: ConnectionFactory
    ): RabbitTemplate {
        val mq = RabbitTemplate(connectionFactory)
        mq.isChannelTransacted = true // 开启事务机制
        return mq
    }

    @Bean
    fun rabbitTransactionManager(
        connectionFactory: ConnectionFactory
    ): RabbitTransactionManager {
        return RabbitTransactionManager(connectionFactory)
    }

}