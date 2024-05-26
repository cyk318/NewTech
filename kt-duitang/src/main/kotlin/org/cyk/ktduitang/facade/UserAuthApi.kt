package org.cyk.ktduitang.facade

import org.cyk.ktduitang.application.IUserAuthBiz
import org.cyk.ktduitang.infra.config.ApiResp
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user/auth/")
class UserAuthApi(
    val userAuthBiz: IUserAuthBiz
) {

    @PostMapping("/login")
    fun login(
        dto: LoginDto
    ): ApiResp<String> {
        val result = userAuthBiz.login(dto)
        return ApiResp.ok(result)
    }

}

data class LoginDto (
    val username: String,
    val password: String,
)

