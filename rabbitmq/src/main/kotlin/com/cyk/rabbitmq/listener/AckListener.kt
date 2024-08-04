package com.cyk.rabbitmq.listener

import com.cyk.rabbitmq.constants.MQConst
import com.rabbitmq.client.Channel //注意这里的依赖
import org.springframework.amqp.core.Message //注意这里的依赖
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.nio.charset.Charset

@Component
class AckListener {

    @RabbitListener(queues = [MQConst.ACK_QUEUE])
    fun handMessage(
        message: Message,
        channel: Channel,
    ) {
        val deliveryTag = message.messageProperties.deliveryTag
        try {
            println("接收到消息: ${String(message.body, Charset.forName("UTF-8"))}, $deliveryTag")
            //业务处理...
            println("业务逻辑处理完成")
            channel.basicAck(deliveryTag, false)
        } catch (e: Exception) {
            channel.basicNack(deliveryTag, false, false) //requeue: false
        }
    }

}