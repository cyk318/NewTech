package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.springframework.stereotype.Component

data class AdminRegCmd (
    val username: String,
)

@Component
class AdminRegHandler: ApplicationHandler<AdminRegCmd, Unit> {

    override fun handler(input: AdminRegCmd) {
        //1.用户名不能重复
        //2.生成随机密码
        //3.落库
    }

}