package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component

data class LoginCmd (
    val username: String,
    val password: String,
)

@Component
class UserLoginHandler(
    private val userInfoRepo: UserInfoRepo,
    private val
): ApplicationHandler<LoginCmd, String> {

    override fun handler(input: LoginCmd): String {
        //1.用户名必须存在
        val user = userInfoRepo.queryByUsername(input.username)
            ?: throw AppException(ApiStatus.USERNAME_OR_PASSWORD_ERROR, "用户名不存在 $input")
        //2.密码必须正确
        if (user.password != input.password) {
            throw AppException(ApiStatus.USERNAME_OR_PASSWORD_ERROR, "用户名不存在 $input")
        }
        //3.生成并保存 token
        //4.记录 ip 信息
    }

}