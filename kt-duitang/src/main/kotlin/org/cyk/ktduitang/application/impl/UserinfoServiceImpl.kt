package org.cyk.ktduitang.application.impl

import org.cyk.ktduitang.application.UserinfoService
import org.cyk.ktduitang.domain.user.service.LoginDomain
import org.cyk.ktduitang.domain.user.service.RegDomain
import org.cyk.ktduitang.facade.model.LoginDto
import org.cyk.ktduitang.facade.model.RegDto
import org.springframework.stereotype.Service

@Service
class UserinfoServiceImpl(
    private val loginDomain: LoginDomain,
    private val regDomain: RegDomain,
): UserinfoService {

    override fun login(dto: LoginDto): String {
        //1.校验用户登录信息是否合法
        val token = loginDomain.ckValidAndGenerateToken(dto)
        //2.根据用户信息生成 token 并保存到 redis 上
        loginDomain.saveTokenToRedis(dto, token)
        //3.返回 token
        return token
    }

    override fun reg(dto: RegDto) {
        //1.校验用户注册信息是否合法
        regDomain.chValid(dto)
        //2.创建新用户信息
        regDomain.createNewUserAndEncrypt(dto)
    }

}