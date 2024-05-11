package org.cyk.user.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestApi {

    @Value("\${server.port}")
    private lateinit var port: String

    @GetMapping("/test")
    fun test() = "user ok! port: $port"

}