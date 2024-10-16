package org.cyk.ktearth.application.user

import jakarta.servlet.http.HttpServletRequest
import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.domain.UserToken
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.domain.user.repo.UserTokenRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.utils.DateUtils
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.cyk.ktearth.service.IPGeoInfoService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

data class LoginCmd (
    val username: String,
    val password: String,
    val request: HttpServletRequest,
)

@Component
class LoginHandler(
    private val userInfoRepo: UserInfoRepo,
    private val userTokenRepo: UserTokenRepo,
    private val ipGeoInfoService: IPGeoInfoService,
): ApplicationHandler<LoginCmd, String> {

    override fun handler(input: LoginCmd): String {
        //1.用户名必须存在
        val user = userInfoRepo.queryByUsername(input.username)
            ?: throw AppException(ApiStatus.USERNAME_OR_PASSWORD_ERROR, "用户名不存在 $input")
        //2.密码必须正确
        if (user.password != input.password) {
            throw AppException(ApiStatus.USERNAME_OR_PASSWORD_ERROR, "密码错误 $input")
        }

        //3.如果用户已经登录，直接返回 token
        val userId = user.id!!
        userTokenRepo.queryByUserId(userId)?.let {
            if (!it.isExpire()) {
                return it.token
            }
        }
        //4.生成并保存 token
        val token = UserTokenUtils.generateTokenByUserId(userId)
        val obj = UserToken (
            userId = userId,
            token = token,
            expireDate = DateUtils.convertToDate(LocalDateTime.now().plusDays(30)),
        )
        userTokenRepo.save(obj)

        //5.记录 ip 信息
        ipGeoInfoService.asyncUpdateIPGeoInfo(input.request, userId)

        log.info("用户登录成功  userId: ${userId}, username: ${user.username}")
        return token
    }

    companion object {
        private val log = LoggerFactory.getLogger(LoginHandler::class.java)
    }

}