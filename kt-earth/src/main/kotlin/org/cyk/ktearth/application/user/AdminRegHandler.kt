package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.domain.UserInfo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.utils.PasswordGenerator
import org.springframework.stereotype.Component
import java.util.Date

data class AdminRegCmd (
    val username: String,
)

@Component
class AdminRegHandler(
    private val userInfoRepo: UserInfoRepo
): ApplicationHandler<AdminRegCmd, Unit> {

    override fun handler(input: AdminRegCmd) {
        //1.用户名不能重复
        userInfoRepo.queryByUsername(input.username)?.let {
            throw AppException(ApiStatus.USERNAME_EXISTS, "该用户名已经存在")
        }
        //2.生成随机密码
        val randomPwd = PasswordGenerator.generate()
        //3.落库
        val now = Date().time
        val obj = input.run {
            UserInfo (
                username = username,
                password = randomPwd,
            )
        }
        userInfoRepo.save(obj)
    }

}