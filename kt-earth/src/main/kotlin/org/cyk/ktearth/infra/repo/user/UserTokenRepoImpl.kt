package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.domain.user.domain.UserToken
import org.cyk.ktearth.domain.user.repo.UserTokenRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

@Document("user_token")
data class UserTokenDo (
    @Id
    val userId: String,
    var token: String,
    var expireDate: Long,
    val cTime: Long,
    var uTime: Long,
)

@Repository
class UserTokenRepoImpl(
    private val mongoTemplate: MongoTemplate,
): UserTokenRepo {

    override fun save(obj: UserToken): UserToken {
        val o = map(obj)
        mongoTemplate.save(o).let { obj.userId = it.userId }
        return obj
    }

    override fun queryByUserId(userId: String): UserToken? {
        val q = Query.query(Criteria.where("_id").`is`(userId))
        return mongoTemplate.findOne(q, UserTokenDo::class.java)?.let { map(it) }
    }

    private fun map(obj: UserTokenDo): UserToken = with(obj) {
        UserToken(
            userId = userId,
            token = token,
            expireDate = Date(expireDate),
            cTime = Date(cTime),
            uTime = Date(uTime),
        )
    }

    private fun map(obj: UserToken): UserTokenDo = with(obj) {
        UserTokenDo(
            userId = userId,
            token = token,
            expireDate = expireDate.time,
            cTime = cTime.time,
            uTime = uTime.time,
        )
    }

}


