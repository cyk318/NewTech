package org.cyk.ktearth.facade.user

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.cyk.ktearth.application.user.LoginCmd
import org.cyk.ktearth.application.user.UserLoginHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    /**
     * 登录
     */
    @PostMapping("/login")
    fun login(
        request: HttpServletRequest,
        @RequestBody @Valid dto: LoginDto,
    ): ApiResp<String> {
        val cmd = LoginCmd(
            request = request,
            username = dto.username,
            password = dto.password,
        )
        val token = userLoginHandler.handler(cmd)
        return ApiResp.ok(token)
    }

}

data class LoginDto (
    @field:Length(min = 2, max = 32)
    val username: String,
    /**
     * 不校验长度的原因: 让暴力破解的人不能锁定密码长度
     */
    @field:NotBlank
    val password: String,
)

