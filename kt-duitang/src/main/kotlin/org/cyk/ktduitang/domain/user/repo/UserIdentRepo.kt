package org.cyk.ktduitang.domain.user.repo

import org.cyk.ktduitang.domain.user.model.UserIdent
import org.cyk.ktduitang.facade.model.RegDto

interface UserIdentRepo {

    fun save(dto: RegDto)
    fun queryByUsername(username: String): UserIdent?

}