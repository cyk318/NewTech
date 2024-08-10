package com.cyk.rabbitmq.constants

object MQConst {

    // 事务
    const val TRANS_QUEUE = "trans.queue"

    // 限流
    const val QOS_QUEUE = "qos.queue"
    const val QOS_EXCHANGE = "qos.exchange"
    const val QOS_BINDING_KEY = "qos.binding.key"

    // 确认应答
    const val ACK_QUEUE = "ack_queue"
    const val ACK_EXCHANGE = "ack_exchange"
    const val ACK_BINDING = "ack_binding"

    // 发送方确认
    const val CONFIRM_QUEUE = "confirm.queue"
    const val CONFIRM_EXCHANGE = "confirm.exchange"
    const val CONFIRM_BINDING = "confirm.binding"

    // 持久化
    const val DURABLE_EXCHANGE = "durable.exchange"
    const val DURABLE_QUEUE = "durable.queue"
    const val DURABLE_BINDING = "durable.binding"

    //ttl
    const val TTL_QUEUE = "ttl.queue"
    const val TTL_EXCHANGE = "ttl.exchange"
    const val TTL_BINDING = "ttl.binding"

}