package org.cyk.ktearth.facade.user

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.cyk.ktearth.application.user.AdminRegHandler
import org.cyk.ktearth.application.user.UserLoginHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.max
import kotlin.math.min

/**
 * 用户权限信息
 * @author yikang.chen
 */
@Validated
@RestController
@RequestMapping("/user/auth")
class UserAuthApi(
    private val userLoginHandler: UserLoginHandler
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid dto: LoginDto,
    ): ApiResp<String> {
        TODO()
    }

}

data class LoginDto (
    @field:Length(min = 2, max = 32)
    val username: String,
    @field:Length(min = 32, max = 32)
    val password: String,
)

