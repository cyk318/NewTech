package org.cyk.ktearth.domain.uact.repo

import org.cyk.ktearth.domain.uact.domain.UserLikes

interface UserLikesRepo {

    fun save(userLikes: UserLikes): UserLikes

    fun queryByUserId(userId: String): UserLikes?


}