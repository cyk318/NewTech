package org.cyk.ktduitang.application

import org.cyk.ktduitang.facade.model.LoginDto
import org.cyk.ktduitang.facade.model.RegDto


interface UserinfoService {

    fun login(dto: LoginDto): String
    fun reg(dto: RegDto)

}