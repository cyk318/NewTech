package com.cyk.rabbitmq.api

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mq5")
class MQ5Api(
    private val rabbitTemplate: RabbitTemplate
) {

    @RequestMapping("/durable")
    fun durable(): String {
        val msg = MessageBuilder
            .withBody("durable msg 1".toByteArray())
            .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
            .build()
        //不用上述这样设计消息也可以，因为 RabbitMQ 消息默认就是持久化的
        rabbitTemplate.convertAndSend(MQConst.DURABLE_EXCHANGE,MQConst.DURABLE_BINDING ,msg)
        return "ok"
    }

}