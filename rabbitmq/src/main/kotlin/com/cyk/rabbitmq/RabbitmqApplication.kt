package com.cyk.rabbitmq

import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
class RabbitmqApplication

fun main(args: Array<String>) {
    runApplication<RabbitmqApplication>(*args)
}


@Configuration
class GlobalConfig {

    @Bean("transRabbitTemplate")
    fun transRabbitTemplate(
        connectionFactory: ConnectionFactory
    ): RabbitTemplate {
        val mq = RabbitTemplate(connectionFactory)
        mq.isChannelTransacted = true // 开启事务机制
        return mq
    }

    @Bean
    fun rabbitTransactionManager(
        connectionFactory: ConnectionFactory
    ): RabbitTransactionManager {
        return RabbitTransactionManager(connectionFactory)
    }

}

@Configuration
class MQTemplateConfig {

    /**
     * 这个配置一定要有！！！(或者有大于等于 2 个的 RabbitTemplate Bean)
     *
     * 这是由于 Autowired 注解自身的原因(以 rabbitmq 为例):
     * 如果配置文件中配置 rabbitmq 相关连接信息，那么 spring 会自动为其创建 RabbitTemplate Bean 对象
     * 如果配置文件中配置 rabbitmq 相关连接信息，而且代码中也配置了一个 RabbitTemplate 的 Bean(名称为 confirmRabbitTemplate)，那么 Spring 将不会自动配置默认的 RabbitTemplate Bean 对象
     * 这就导致，我们无论代码写的注入的是 rabbitTemplate 还是 confirmRabbitTemplate，但实际上注入的都是 confirmRabbitTemplate
     */
    @Bean("rabbitTemplate")
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory
    ): RabbitTemplate {
        return RabbitTemplate(connectionFactory)
    }

    @Bean("confirmRabbitTemplate")
    fun confirmRabbitTemplate(
        connectionFactory: ConnectionFactory
    ): RabbitTemplate {
        val tpl = RabbitTemplate(connectionFactory)
        tpl.setConfirmCallback(RabbitTemplate.ConfirmCallback { correlationData, ack, cause ->
            println("执行了 confirm ...")
            if (ack) {
                println("confirm ack: { 消息id: ${correlationData?.id} }")
            } else {
                println("confirm nack: { 消息id: ${correlationData?.id}, cause: $cause }")
                //进行相应的业务处理...
            }
        })
        //这里可以和 confirm模式 一起配置
        //mandatory = true 属性是在告诉 rabbitmq，如果一个消息无法被任何队列消费，那么该消息就会返回给发送者，此时 ReturnCallback 就会被触发
        //mandatory 相当于是开启 ReturnsCallback 前提
        tpl.setMandatory(true)
        tpl.setReturnsCallback(RabbitTemplate.ReturnsCallback { returned ->
            println("执行了 return ...")
            println("return: $returned")
        })
        return tpl
    }

}