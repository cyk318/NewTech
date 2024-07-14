package org.cyk.prometheus.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloApi {

    @RequestMapping("/hello")
    fun hello(): String {
        return "hello !"
    }

}