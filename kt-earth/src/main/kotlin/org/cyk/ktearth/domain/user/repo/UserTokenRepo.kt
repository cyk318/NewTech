package org.cyk.ktearth.domain.user.repo

import org.cyk.ktearth.infra.repo.user.SaveUserTokenCmd

interface UserTokenRepo {

    fun save(cmd: SaveUserTokenCmd)

    fun getTokenByUserId(userId: String): String?

}