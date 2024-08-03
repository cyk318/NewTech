package com.cyk.rabbitmq.api

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mq")
class MQApi(
    val rabbitTemplate: RabbitTemplate,
    val transRabbitTemplate: RabbitTemplate,
) {

    @RequestMapping("/trans-close")
    fun transClose(): String {
        rabbitTemplate.convertAndSend("", MQConst.TRANS_QUEUE, "trans msg 1")
        //触发异常
        val a = 1 / 0
        rabbitTemplate.convertAndSend("", MQConst.TRANS_QUEUE, "trans msg 1")
        return "ok"
    }

    @Transactional
    @RequestMapping("/trans-open")
    fun transOpen(): String {
        transRabbitTemplate.convertAndSend("", MQConst.TRANS_QUEUE, "trans msg 1")
        //触发异常
        val a = 1 / 0
        transRabbitTemplate.convertAndSend("", MQConst.TRANS_QUEUE, "trans msg 1")
        return "ok"
    }

    @RequestMapping("/qos")
    fun qos(): String {
        for (i in 1..20) {
            rabbitTemplate.convertAndSend(MQConst.QOS_EXCHANGE, MQConst.QOS_BINDING_KEY, "qos msg $i")
        }
        return "ok"
    }

}