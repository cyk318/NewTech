package org.cyk.ktduitang.application

import org.cyk.ktduitang.facade.LoginDto


interface UserAuthBiz {

    fun login(dto: LoginDto): String

}