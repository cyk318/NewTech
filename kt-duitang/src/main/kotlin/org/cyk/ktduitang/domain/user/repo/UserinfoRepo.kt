package org.cyk.ktduitang.domain.user.repo

import org.cyk.ktduitang.domain.user.model.UserIdent

interface UserinfoRepo {

    fun queryUserIdentByUsername(username: String): UserIdent?

}