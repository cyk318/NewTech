package org.cyk.ktearth.domain.user.repo

import org.cyk.ktearth.domain.user.domain.UserToken

interface UserTokenRepo {

    fun save(obj: UserToken): UserToken

    fun queryByUserId(userId: String): UserToken?

}