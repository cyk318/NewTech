package com.cyk.rabbitmq.infra

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MQConfig {

    @Bean
    fun transQueue() = Queue(MQConst.TRANS_QUEUE)

}