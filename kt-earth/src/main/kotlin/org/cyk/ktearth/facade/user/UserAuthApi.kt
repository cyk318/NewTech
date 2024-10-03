package org.cyk.ktearth.facade.user

import org.cyk.ktearth.application.user.AdminRegHandler
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户权限 API
 * @author yikang.chen
 */
@Validated
@RestController
@RequestMapping("/user/auth")
class UserAuthApi(
    private val adminRegHandler: AdminRegHandler,
) {

    @PostMapping("/login")
    fun login(

    ) {

    }

}

data class LoginDto (
    val username: String,
    val password: String,
)

