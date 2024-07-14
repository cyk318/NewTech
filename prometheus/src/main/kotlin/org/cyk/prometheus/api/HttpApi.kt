package org.cyk.prometheus.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HttpApi {

//    {
//        "targets": ["env-base:9100"],
//        "labels": {
//        "env": "prod",
//        "job": "node_exporter"
//    }
//    },
//    {
//        "targets": ["env-base:9104", "env-base:9121"],
//        "labels": {
//        "env": "prod",
//        "job": "mysql_and_redis_exporter"
//    }

    @RequestMapping("/targets")
    fun targets(): List<PrometheusVo> {
        //1. 从数据库中获取对应的 套接字 信息，
        //...

        //2. 组装 json 数据
        val v1 = PrometheusVo(
            targets = listOf("env-base:9100"),
            labels = Labels(
                env = "prod",
                job = "node_exporter"
            )
        )
        val v2 = PrometheusVo(
            targets = listOf("env-base:9104", "env-base:9121"),
            labels = Labels(
                env = "prod",
                job = "mysql_and_redis_exporter"
            )
        )
        return listOf(v1, v2)
    }

}

data class PrometheusVo (
    val targets: List<String>,
    val labels: Labels
)

//注意，根据 prometheus 的要求，不允许值的第一个字符为数字！！
data class Labels (
    val env: String,
    val job: String
)