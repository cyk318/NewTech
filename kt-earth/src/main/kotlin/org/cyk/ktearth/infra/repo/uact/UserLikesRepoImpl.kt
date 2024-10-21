package org.cyk.ktearth.infra.repo.uact

import org.cyk.ktearth.domain.uact.domain.Like
import org.cyk.ktearth.domain.uact.domain.UserLikes
import org.cyk.ktearth.domain.uact.repo.UserLikesRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

@Document("user_likes")
data class UserLikesDo(
    @Id
    val userId: String,
    val likes: MutableList<Like>,
    val cTime: Long,
    var uTime: Long,
)

@Repository
class UserLikesRepoImpl(
    private val mongoTemplate: MongoTemplate
): UserLikesRepo {

    override fun save(userLikes: UserLikes): UserLikes {
        val obj = map(userLikes)
        mongoTemplate.save(obj)
        return userLikes
    }


    override fun queryByUserId(userId: String): UserLikes? {
        val q = Query.query(Criteria.where("_id").`is`(userId))
        return mongoTemplate.findOne(q, UserLikesDo::class.java)?.let { map(it) }
    }

    private fun map(it: UserLikesDo): UserLikes = with(it) {
        UserLikes(
            userId = userId,
            likes = likes,
            cTime = Date(cTime),
            uTime = Date(uTime),
        )
    }

    private fun map(it: UserLikes): UserLikesDo = with(it) {
        UserLikesDo(
            userId = userId,
            likes = likes,
            cTime = cTime.time,
            uTime = uTime.time,
        )
    }

}