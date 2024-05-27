package org.cyk.ktduitang.application

import org.cyk.ktduitang.facade.model.LoginDto
import org.cyk.ktduitang.facade.model.UserRegDto


interface UserinfoService {

    fun login(dto: LoginDto): String
    fun reg(dto: UserRegDto)

}