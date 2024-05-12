package org.cyk.product.api

import org.cyk.feign.UserClientRpc
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class TestApi(
    var userClientRpc: UserClientRpc
) {

    @Value("\${server.port}")
    private lateinit var port: String

    @GetMapping("/test")
    fun test() = "product ok! port: $port"

    @GetMapping("/test/feign")
    fun testFeign(): String {
        return "user: ${userClientRpc.test()}, product: product ok!"
    }

}
