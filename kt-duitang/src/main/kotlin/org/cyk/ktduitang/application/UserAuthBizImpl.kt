package org.cyk.ktduitang.application

import org.cyk.ktduitang.facade.LoginDto
import org.springframework.stereotype.Service

@Service
class UserAuthBizImpl: IUserAuthBiz {

    override fun login(dto: LoginDto): String {
        //1.校验用户信息是否合法
        //2.根据用户信息生成 token 并保存到 redis 上
        //3.返回 token
    }


}