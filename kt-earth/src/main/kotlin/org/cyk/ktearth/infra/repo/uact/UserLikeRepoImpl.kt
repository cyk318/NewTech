package org.cyk.ktearth.infra.repo.uact

import org.cyk.ktearth.domain.uact.domain.Like
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Repository
import java.util.*

@Document
data class UserLikesDo(
    val userId: String,
    val likes: MutableList<Like> = mutableListOf(),
    val cTime: Date = Date(),
    var uTime: Date = Date(),
)

@Repository
class UserLikeRepoImpl(
    private val mongoTemplate: MongoTemplate
) {



}