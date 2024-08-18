package com.cyk.rabbitmq.infra

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuorumConfig {

    @Bean
    fun quorumQueue(): Queue = QueueBuilder
        .durable(MQConst.QUORUM_QUEUE)
        .quorum()
        .build()

}