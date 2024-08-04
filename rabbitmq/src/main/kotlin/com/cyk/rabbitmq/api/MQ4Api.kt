package com.cyk.rabbitmq.api

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mq4")
class MQ4Api(
    val confirmRabbitTemplate: RabbitTemplate
) {

    @RequestMapping("/confirm")
    fun confirm(): String {
        val data = CorrelationData("1")
        confirmRabbitTemplate.convertAndSend(MQConst.CONFIRM_EXCHANGE, MQConst.CONFIRM_BINDING + "1", "confirm msg 1", data)
        return "ok"
    }

}
