package com.cyk.rabbitmq.infra

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MQConfig {

    @Bean
    fun transQueue() = Queue(MQConst.TRANS_QUEUE)

    @Bean
    fun qosExchange() = DirectExchange(MQConst.QOS_EXCHANGE)
    @Bean
    fun qosQueue() = Queue(MQConst.QOS_QUEUE)
    @Bean
    fun qosBinding(): Binding = BindingBuilder
        .bind(qosQueue())
        .to(qosExchange())
        .with(MQConst.QOS_BINDING_KEY)

}