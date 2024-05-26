package org.cyk.ktduitang.domain.user.service

import org.cyk.ktduitang.domain.user.repo.UserRepoImpl
import org.cyk.ktduitang.facade.LoginDto
import org.cyk.ktduitang.infra.config.ApiStatus
import org.cyk.ktduitang.infra.config.AppException
import org.cyk.ktduitang.infra.tools.PasswordUtils
import org.springframework.stereotype.Component

@Component
class LoginDomain(
    val userRepoImpl: UserRepoImpl
) {

    fun ckValid(dto: LoginDto) {
        val userinfo = userRepoImpl.queryUserIdentByUsername(dto.username)
            ?: throw AppException(ApiStatus.INVALID_PARAM, "用户名不存在！username: ${dto.username}")
        if (!PasswordUtils.isOk(dto.password, userinfo.password)) {
            throw AppException(ApiStatus.INVALID_PARAM, "密码错误！username: ${dto.username}, password: ${dto.password}")
        }
    }

    fun generateTokenAndSaveRedis(dto: LoginDto): String {
        TODO("Not yet implemented")
    }

}

