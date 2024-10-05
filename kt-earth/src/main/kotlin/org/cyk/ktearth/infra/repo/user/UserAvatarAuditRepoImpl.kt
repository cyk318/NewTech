package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.domain.user.domain.UserAvatarAudit
import org.cyk.ktearth.domain.user.repo.UserAvatarAuditRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Document("user_avatar_audit")
data class UserAvatarAuditDo (
    @Id
    val userId: String,
    val avatar: String,
    val cTime: Long,
)

@Repository
class UserAvatarAuditRepoImpl(
    private val mongoTemplate: MongoTemplate,
) : UserAvatarAuditRepo {

    override fun save(o: UserAvatarAudit): UserAvatarAudit {
        val obj = map(o)
        mongoTemplate.save(obj)
        return o
    }

    override fun removeById(id: String) {
        val q = Query.query(Criteria.where("_id").`is`(id))
        mongoTemplate.remove(q, UserAvatarAuditDo::class.java)
    }

    override fun exists(id: String): Boolean {
        val q = Query.query(Criteria.where("_id").`is`(id))
        return mongoTemplate.exists(q, UserAvatarAuditDo::class.java)
    }

    override fun queryById(id: String): UserAvatarAudit? {
        val q = Query.query(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(q, UserAvatarAuditDo::class.java)?.let { map(it) }
    }

    private fun map(o: UserAvatarAuditDo): UserAvatarAudit = with(o) {
        UserAvatarAudit(
            userId, avatar, cTime
        )
    }

    private fun map(o: UserAvatarAudit): UserAvatarAuditDo = with(o) {
        UserAvatarAuditDo(
            userId, avatar, cTime
        )
    }

}