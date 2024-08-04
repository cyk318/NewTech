package com.cyk.rabbitmq.infra

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.beans.factory.annotation.Qualifier
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

    @Bean("ackExchange")
    fun ackExchange() = DirectExchange(MQConst.ACK_EXCHANGE)
    @Bean("ackQueue")
    fun ackQueue() = Queue(MQConst.ACK_QUEUE)
    @Bean
    fun ackBinding(
        @Qualifier("ackExchange") exchange: DirectExchange,
        @Qualifier("ackQueue") queue: Queue,
    ): Binding {
        return BindingBuilder
            .bind(queue)
            .to(exchange)
            .with(MQConst.ACK_BINDING)
    }

    @Bean("confirmExchange")
    fun confirmExchange() = DirectExchange(MQConst.CONFIRM_EXCHANGE)
    @Bean("confirmQueue")
    fun confirmQueue() = Queue(MQConst.CONFIRM_QUEUE)
    @Bean("confirmBinding")
    fun confirmBinding(
        @Qualifier("confirmExchange") exchange: DirectExchange,
        @Qualifier("confirmQueue") queue: Queue,
    ): Binding = BindingBuilder
        .bind(queue)
        .to(exchange)
        .with(MQConst.CONFIRM_BINDING)

    @Bean("durableExchange")
    fun durableExchange(): DirectExchange = ExchangeBuilder
        .directExchange(MQConst.DURABLE_EXCHANGE)
        .durable(true) //持久化
        .build()
    @Bean("durableQueue")
    fun durableQueue(): Queue = QueueBuilder
        .durable(MQConst.DURABLE_QUEUE) //持久化
        .build()
    @Bean("durableBinding")
    fun durableBinding(): Binding = BindingBuilder
        .bind(durableQueue())
        .to(durableExchange())
        .with(MQConst.DURABLE_BINDING)
}

