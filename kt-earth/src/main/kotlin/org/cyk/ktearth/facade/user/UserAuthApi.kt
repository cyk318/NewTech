package org.cyk.ktearth.facade.user

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.cyk.ktearth.application.user.LoginCmd
import org.cyk.ktearth.application.user.LoginHandler
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.domain.user.repo.UserTokenRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.hibernate.validator.constraints.Length
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户
 * @author yikang.chen
 */
@Validated
@RestController
@RequestMapping("/user/auth")
class UserAuthApi(
    private val loginHandler: LoginHandler,
    private val userTokenRepo: UserTokenRepo,
    private val userInfoRepo: UserInfoRepo,
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
        val token = loginHandler.handler(cmd)
        return ApiResp.ok(token)
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
    ): ApiResp<Unit> {
        val userId = UserTokenUtils.getUserIdByRequest(request)
        val username = userInfoRepo.queryById(userId)!!.username

        // 将 token 以当前时间设置为过期
        val userToken = userTokenRepo.queryByUserId(userId)
            ?: throw AppException(ApiStatus.NOT_LOGIN, "用户未登录")
        userToken.makeExpireNow()
        userTokenRepo.save(userToken)

        log.info("用户退出登录  userId: $userId, username: $username")
        return ApiResp.ok()
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserAuthApi::class.java)
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

