package org.cyk.ktearth.infra.repo.uact

import org.cyk.ktearth.domain.uact.domain.CommentParentIdType
import org.cyk.ktearth.domain.uact.domain.CommentStatus
import org.cyk.ktearth.infra.utils.DateUtils
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Document("comment_info")
data class CommentInfoDo (
    @Id
    val id: String? = null,
    val articleId: String,
    val parentId: CommentParentIdType = CommentParentIdType.TOP, //父评论id，默认为顶级评论 val userId: String,
    val userId: String,
    val content: String,
    val status: CommentStatus = CommentStatus.AUDIT, //默认状态为 审核
    val cTime: Long,
    val uTime: Long,
)

//@Repository
//class CommentInfoRepoImpl(
//    private val mongoTemplate: MongoTemplate,
//): CommentInfoRepo {
//
//    override fun save(cmd: CommentSaveToAuditCmd) {
//        val obj = map(cmd)
//        mongoTemplate.save(obj)
//    }
//
//    override fun updateStatusByCommentId(commentId: String, status: Int) {
//        val q = Query.query(Criteria.where("_id").`is`(commentId))
//        val u = Update().set("status", status)
//        mongoTemplate.updateFirst(q, u, CommentInfoDo::class.java)
//    }
//
//    override fun exists(commentId: String): Boolean {
//        val q = Query.query(Criteria.where("_id").`is`(commentId))
//        return mongoTemplate.exists(q, CommentInfoDo::class.java)
//    }
//
//    override fun queryByCommentId(commentId: String): CommentInfo? {
//        val q = Query.query(Criteria.where("_id").`is`(commentId))
//        return mongoTemplate.findOne(q, CommentInfoDo::class.java)?.let { map(it) }
//    }
//
//
//    private fun map(it: CommentInfoDo): CommentInfo = with(it) {
//        CommentInfo(
//            id = id!!,
//            articleId = articleId,
//            parentId = parentId,
//            userId = userId,
//            content = content,
//            status = status,
//            cTime = cTime,
//            uTime = uTime,
//        )
//    }
//
//    private fun map(it: CommentSaveToAuditCmd): CommentInfoDo = with(it) {
//        CommentInfoDo(
//            articleId = articleId,
//            parentId = parentId ?: CommentParentIdType.TOP.id,
//            userId = userId,
//            content = content,
//            status = status,
//            cTime = DateUtils.mongoNowTime(),
//            uTime = DateUtils.mongoNowTime(),
//        )
//    }
//
//}
//
//
