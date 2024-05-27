package org.cyk.ktduitang.domain.user.repo

import org.cyk.ktduitang.domain.user.model.UserIdent
import org.cyk.ktduitang.domain.user.model.UserIdentDo
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserinfoRepoImpl (
    val mongoTemplate: MongoTemplate,
): UserinfoRepo {

    override fun queryUserIdentByUsername(username: String): UserIdent? {
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

}
