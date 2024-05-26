package org.cyk.ktduitang.application

import org.cyk.ktduitang.facade.LoginDto


interface IUserAuthBiz {

    fun login(dto: LoginDto): String

}