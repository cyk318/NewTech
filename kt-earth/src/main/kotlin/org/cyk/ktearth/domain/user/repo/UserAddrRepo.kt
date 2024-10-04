package org.cyk.ktearth.domain.user.repo

import org.cyk.ktearth.service.SaveOrUpdateUserAddrCmd

interface UserAddrRepo {

    fun saveOrUpdateUserAddr(cmd: SaveOrUpdateUserAddrCmd)

    fun existsUserAddrByUserId(userId: String): Boolean

}

