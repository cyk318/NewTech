package com.cyk.rabbitmq.listener

import com.cyk.rabbitmq.constants.MQConst
import com.rabbitmq.client.Channel //注意这里的依赖
import org.springframework.amqp.core.Message //注意这里的依赖
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.nio.charset.Charset

//@Component
class AckListener {

    @RabbitListener(queues = [MQConst.ACK_QUEUE])
    fun handMessage(
        message: Message,
        channel: Channel,
    ) {
        println("接收到消息: ${String(message.body, Charset.forName("UTF-8"))}, ${message.messageProperties.deliveryTag}")
        val a = 1 / 0
    }

//    @RabbitListener(queues = [MQConst.ACK_QUEUE])
//    fun handMessage(
//        message: Message,
//        channel: Channel,
//    ) {
//        val deliveryTag = message.messageProperties.deliveryTag
//        try {
//            println("接收到消息: ${String(message.body, Charset.forName("UTF-8"))}, $deliveryTag")
//            val a = 1 / 0
//            channel.basicAck(deliveryTag, false)
//        } catch (e: Exception) {
//            //通过返回 nack，并设置 requeue 为 ture 实现消息重新入队，并进行重试
//            channel.basicNack(deliveryTag, false, true)
//        }
//    }

}