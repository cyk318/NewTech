package org.cyk.ktduitang.facade

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.cyk.ktduitang.application.UserAuthService
import org.cyk.ktduitang.infra.config.ApiResp
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/auth/")
class UserAuthApi(
    val userAuthService: UserAuthService
) {

    @PostMapping("/login")
    fun login(
        @Valid dto: LoginDto
    ): ApiResp<String> {
        val result = userAuthService.login(dto)
        return ApiResp.ok(result)
    }

}

data class LoginDto (
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)

