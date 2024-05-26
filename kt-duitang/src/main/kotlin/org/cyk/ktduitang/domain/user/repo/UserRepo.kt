package org.cyk.ktduitang.domain.user.repo

import org.cyk.ktduitang.domain.user.model.UserIdent

interface UserRepo {

    fun queryUserIdentByUsername(username: String): UserIdent?

}