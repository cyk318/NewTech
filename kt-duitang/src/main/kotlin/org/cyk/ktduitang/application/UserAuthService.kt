package org.cyk.ktduitang.application

import org.cyk.ktduitang.facade.LoginDto


interface UserAuthService {

    fun login(dto: LoginDto): String

}