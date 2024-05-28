package org.cyk.ktduitang.domain.user.repo.impl

import org.cyk.ktduitang.domain.user.model.UserDetail
import org.cyk.ktduitang.domain.user.model.UserDetailDo
import org.cyk.ktduitang.domain.user.repo.UserDetailRepo
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class UserDetailRepoImpl(
    private val mongoTemplate: MongoTemplate,
): UserDetailRepo {
    override fun save(id: String) {
        val o = map(id)
        mongoTemplate.save(o)
    }

    private fun map(id: String): UserDetailDo {
        return UserDetailDo(
            id = id
        )
    }

}