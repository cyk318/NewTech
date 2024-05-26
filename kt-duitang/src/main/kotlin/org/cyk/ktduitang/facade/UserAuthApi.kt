package org.cyk.ktduitang.facade

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user/auth/")
class UserAuthApi {

    @PostMapping("/login")
    fun login() {

    }

}