package org.cyk.ktduitang.domain.user.repo.impl

import org.cyk.ktduitang.domain.user.model.UserIdent
import org.cyk.ktduitang.domain.user.model.UserIdentDo
import org.cyk.ktduitang.domain.user.repo.UserIdentRepo
import org.cyk.ktduitang.facade.model.RegDto
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserIdentRepoImpl (
    private val mongoTemplate: MongoTemplate,
): UserIdentRepo {

    override fun save(dto: RegDto) {
        val o = map(dto)
        val result = mongoTemplate.save(o)
        dto.id = result.id
    }

    override fun queryByUsername(username: String): UserIdent? {
        val c = Criteria.where("username").`is`(username)
        return mongoTemplate.findOne(Query.query(c), UserIdentDo::class.java)
            ?.let(::map)
    }

    private fun map(o: UserIdentDo): UserIdent = with(o) {
        UserIdent(
            id = id!!,
            username = username,
            password = password,
            state = state,
            cTime = cTime,
            uTime = uTime,
        )
    }

    private fun map(o: RegDto):  UserIdentDo = with(o) {
        UserIdentDo(
            username = username,
            password = password,
        )
    }

}
