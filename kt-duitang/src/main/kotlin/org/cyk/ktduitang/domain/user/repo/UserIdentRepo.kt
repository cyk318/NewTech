package org.cyk.ktduitang.domain.user.repo

import org.cyk.ktduitang.domain.user.model.UserIdent

interface UserIdentRepo {

    fun queryByUsername(username: String): UserIdent?

}