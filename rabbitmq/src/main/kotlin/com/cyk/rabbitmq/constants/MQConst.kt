package com.cyk.rabbitmq.constants

object MQConst {

    // 事务
    const val TRANS_QUEUE = "trans.queue"

    // 限流
    const val QOS_QUEUE = "qos.queue"
    const val QOS_EXCHANGE = "qos.exchange"
    const val QOS_BINDING_KEY = "qos.binding.key"

}