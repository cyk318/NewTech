package org.cyk.ktduitang.application.impl

import org.cyk.ktduitang.application.UserAuthBiz
import org.cyk.ktduitang.domain.user.service.LoginDomain
import org.cyk.ktduitang.facade.LoginDto
import org.springframework.stereotype.Service

@Service
class UserAuthBizImpl(
    val loginDomain: LoginDomain
): UserAuthBiz {

    override fun login(dto: LoginDto): String {
        //1.校验用户登录信息是否合法
        loginDomain.ckValid(dto)
        //2.根据用户信息生成 token 并保存到 redis 上
        val token = loginDomain.generateTokenAndSaveRedis(dto)
        //3.返回 token
        return token
    }

}