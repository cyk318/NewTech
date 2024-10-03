package org.cyk.ktearth.infra.repo.uact

import org.cyk.ktearth.infra.utils.DateUtils
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Date

/**
 * 评论统计表 (只有顶级评论才会这个信息)
 */
@Document("comment_stat")
data class CommentStatDo (
    @Id
    val commentId: String,
    val likeCnt: Long = 0,
    val commentCnt: Long = 0,
    val uTime: Long,
    val cTime: Long,
)

//@Repository
//class CommentStatRepoImpl(
//    private val mongoTemplate: MongoTemplate,
//): CommentStatRepo {
//
//    override fun save(id: String) {
//        val obj = map(id)
//        mongoTemplate.save(obj)
//    }
//
//    override fun commentCntIncr(commentId: String) {
//        val q = Query.query(Criteria.where("_id").`is`(commentId))
//        val u = Update()
//            .inc("comment_cnt", 1)
//            .set("u_time", DateUtils.mongoNowTime())
//        mongoTemplate.updateFirst(q, u, CommentStatDo::class.java)
//    }
//
//    private fun map(id: String): CommentStatDo {
//        return CommentStatDo(
//            commentId = id,
//            cTime = Date().time,
//            uTime = Date().time,
//        )
//    }
//
//
//}
//
