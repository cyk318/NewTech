package com.cyk.rabbitmq.api

import com.cyk.rabbitmq.constants.MQConst
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mq")
class MQApi(
    private val rabbitTemplate: RabbitTemplate
) {

    @RequestMapping("ttl")
    fun ttl(): String {
        rabbitTemplate.convertAndSend(MQConst.TTL_EXCHANGE, MQConst.TTL_BINDING, "ttl msg 1") { msg ->
            //给 msg 配置一些属性
            val expire = 20 * 1000
            msg.messageProperties.expiration = expire.toString()
            return@convertAndSend msg
        }
        return "ok"
    }

}