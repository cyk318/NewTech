package com.cyk.rabbitmq.api

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mq3")
class MQ3Api(
   val rabbitTemplate: RabbitTemplate
) {

    @RequestMapping("/ack")
    fun ack(): String {
        rabbitTemplate.convertAndSend(MQConst.ACK_EXCHANGE, MQConst.ACK_BINDING, "ack msg 1")
        return "ok"
    }

}