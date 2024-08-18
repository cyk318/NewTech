package com.cyk.rabbitmq.api

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mq")
class MQApi(
    private val rabbitTemplate: RabbitTemplate
) {

    @GetMapping
    fun quorum(): String {
        rabbitTemplate.convertAndSend("", MQConst.QUORUM_QUEUE, "quorum msg 1")
        return "ok"
    }

}