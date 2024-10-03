package org.cyk.ktearth.infra.repo.uact

import org.cyk.ktearth.infra.utils.DateUtils
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Document("article_like")
data class ArticleLikeDo (
    @Id
    val id: String? = null,
    val articleId: String,
    val userId: String,
    val uTime: Long
)

//@Repository
//class LikeRepoImpl(
//    private val mongoTemplate: MongoTemplate,
//): LikeRepo {
//
//    override fun save(cmd: ArticleLikeCmd) {
//        val obj = map(cmd)
//        mongoTemplate.save(obj)
//    }
//
//    override fun delArticleLikeById(articleId: String, userId: String) {
//        val c = Criteria()
//        c.and("article_id").`is`(articleId)
//        c.and("user_id").`is`(userId)
//        mongoTemplate.remove(Query.query(c), ArticleLikeDo::class.java)
//    }
//
//    override fun exists(articleId: String, userId: String): Boolean {
//        val c = Criteria()
//        c.and("article_id").`is`(articleId)
//        c.and("user_id").`is`(userId)
//        return mongoTemplate.exists(Query.query(c), ArticleLikeDo::class.java)
//    }
//
//    private fun map(o: ArticleLikeDo): ArticleLike = with(o) {
//        ArticleLike(
//            id = id!!,
//            articleId = articleId,
//            userId = userId,
//            uTime = uTime,
//        )
//    }
//
//    private fun map(cmd: ArticleLikeCmd): ArticleLikeDo = with(cmd) {
//        ArticleLikeDo(
//            articleId = articleId,
//            userId = userId,
//            uTime = DateUtils.mongoNowTime()
//        )
//    }
//
//}
//
//
