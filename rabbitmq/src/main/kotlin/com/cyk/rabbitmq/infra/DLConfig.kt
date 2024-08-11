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

/**
 * Ps：注意，队列被修改属性之后需要在MQ 管理平台删除重名队列，然后等程序启动，自动重新创建
 */
@Configuration
class DLConfig {

    @Bean
    fun normalExchange(): DirectExchange = ExchangeBuilder
        .directExchange(MQConst.NORMAL_EXCHANGE)
        .build()
    @Bean
    fun normalQueue(): Queue = QueueBuilder
        .durable(MQConst.NORMAL_QUEUE)
        .deadLetterExchange(MQConst.DL_EXCHANGE)
        .deadLetterRoutingKey(MQConst.DL_BINDING)
        .ttl(10 * 1000)
        //.maxLength(10L)  //队列设置最大长度
        .build()
    @Bean
    fun normalBinding(
        @Qualifier("normalExchange") exchange: Exchange,
        @Qualifier("normalQueue") queue: Queue,
    ): Binding = BindingBuilder
        .bind(queue)
        .to(exchange)
        .with(MQConst.NORMAL_BINDING)
        .noargs() //如果 交换机 是顶级接口，这里需要 noargs()

    @Bean
    fun dlExchange(): DirectExchange = ExchangeBuilder
        .directExchange(MQConst.DL_EXCHANGE)
        .build()
    @Bean
    fun dlQueue(): Queue = QueueBuilder
        .durable(MQConst.DL_QUEUE)
        .build()
    @Bean
    fun dlBinding(): Binding = BindingBuilder
        .bind(dlQueue())
        .to(dlExchange())
        .with(MQConst.DL_BINDING)

}