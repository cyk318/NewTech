package org.cyk.ktearth.domain.user.repo

import org.cyk.ktearth.domain.user.domain.UserAvatarAudit

interface UserAvatarAuditRepo {

    fun save(o: UserAvatarAudit): UserAvatarAudit

    fun removeById(id: String)

    fun exists(id: String): Boolean

}