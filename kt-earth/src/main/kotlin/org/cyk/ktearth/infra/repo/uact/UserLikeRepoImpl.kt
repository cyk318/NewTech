package org.cyk.ktearth.infra.repo.uact

import org.cyk.ktearth.domain.uact.domain.Like
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Repository
import java.util.*

@Document("user_likes")
data class UserLikesDo(
    @Id
    val userId: String,
    val likes: MutableList<Like>,
    val cTime: Date,
    var uTime: Date,
)

@Repository
class UserLikeRepoImpl(
    private val mongoTemplate: MongoTemplate
) {



}