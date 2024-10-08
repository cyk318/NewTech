package org.cyk.tailscaletest.api

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
@CrossOrigin
class Test {

    @GetMapping("/{name}")
    fun hello(
        @PathVariable("name") name: String,
    ): String {
        return "hello $name ~"
    }


}