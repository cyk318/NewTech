package com.cyk.rabbitmq.listener

import com.cyk.rabbitmq.constants.MQConst
import com.rabbitmq.client.Channel
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

//@Component
class QosListener {

    @RabbitListener(queues = [MQConst.QOS_QUEUE])
    fun fastHandMessage(
        message: Message,
        channel: Channel
    ) {
        val deliverTag = message.messageProperties.deliveryTag
        try {
            println("接收到消息: ${String(message.body, charset("UTF-8"))}, ${message.messageProperties.messageId}")
            Thread.sleep(1000)
            println("正式工: 任务处理完成！")
            channel.basicAck(deliverTag, false)
        } catch (e: Exception) {
            channel.basicNack(deliverTag, false, true)
        }
    }

    @RabbitListener(queues = [MQConst.QOS_QUEUE])
    fun slowHandMessage(
        message: Message,
        channel: Channel
    ) {
        val deliverTag = message.messageProperties.deliveryTag
        try {
            println("接收到消息: ${String(message.body, charset("UTF-8"))}, ${message.messageProperties.messageId}")
            Thread.sleep(2000)
            println("实习生: 任务处理完成！")
            channel.basicAck(deliverTag, false)
        } catch (e: Exception) {
            channel.basicNack(deliverTag, false, true)
        }
    }

}