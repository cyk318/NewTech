package org.cyk.ktduitang.application.impl

import org.cyk.ktduitang.application.UserAuthService
import org.cyk.ktduitang.domain.user.service.LoginDomain
import org.cyk.ktduitang.facade.LoginDto
import org.springframework.stereotype.Service

@Service
class UserAuthServiceImpl(
    val loginDomain: LoginDomain
): UserAuthService {

    override fun login(dto: LoginDto): String {
        //1.校验用户登录信息是否合法
        val token = loginDomain.ckValidAndGenerateToken(dto)
        //2.根据用户信息生成 token 并保存到 redis 上
        loginDomain.saveTokenToRedis(dto, token)
        //3.返回 token
        return token
    }

}