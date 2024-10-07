package org.cyk.ktearth.infra.repo.article

import org.cyk.ktearth.domain.article.domain.ArticleInfo
import org.cyk.ktearth.domain.article.domain.ArticleType
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Document("article_info")
data class ArticleInfoDo (
    @Id
    val id: String?,
    val authorId: String,
    val title: String,
    val content: String,
    val cover: String, //文章封面
    val label: List<String>, //标签
    val type: ArticleType, //文章类型
    val cTime: Long,
    val uTime: Long,
)

@Repository
class ArticleInfoRepoImpl(
    private val mongoTemplate: MongoTemplate,
): ArticleInfoRepo {

    override fun save(o: ArticleInfo) {
        val obj = map(o)
        mongoTemplate.save(obj).let { o.id = it.id!! }
    }

//    override fun page(cmd: ArticlePageCmd): PageResp<ArticleInfo> {
//        val c = Criteria()
//        val q = Query.query(c)
//        cmd.run {
//            c.and("type").`is`(type)
//            if (label.isNotEmpty()) {
//                c.and("label").`in`(label)
//            }
//            if (!targetUserId.isNullOrBlank()) {
//                c.and("author_id").`is`(targetUserId)
//            }
//            q.skip(start * 1L).limit(limit + 1)
//        }
//        val result = mongoTemplate.find(q, ArticleInfoDo::class.java)
//            .map { map(it) }
//            .toMutableList()
//        val hasMore = result.size == cmd.limit + 1
//        if (hasMore) {
//            result.removeLast()
//        }
//        return PageResp.ok(
//            hasMore,
//            (cmd.start + cmd.limit).toLong(),
//            result,
//            null
//        )
//    }

    override fun queryById(articleId: String): ArticleInfo? {
        val q = Query.query(Criteria.where("_id").`is`(articleId))
        return mongoTemplate.findOne(q, ArticleInfoDo::class.java)?.let { map(it) }
    }

    override fun exists(articleId: String): Boolean {
        val q = Query.query(Criteria.where("_id").`is`(articleId))
        return mongoTemplate.exists(q, ArticleInfoDo::class.java)
    }

    override fun delById(articleId: String) {
        val q = Query.query(Criteria.where("_id").`is`(articleId))
        mongoTemplate.remove(q, ArticleInfoDo::class.java)
    }

    override fun update(o: ArticleInfo) {
        val q = Query.query(Criteria.where("_id").`is`(o.id))
        val u = Update().run {
            set("title", o.title)
            set("content", o.content)
            set("cover", o.cover)
            set("label", o.label)
            set("type", o.type)
            set("u_time", o.uTime)
        }
        mongoTemplate.updateFirst(q, u, ArticleInfoDo::class.java)
    }

    private fun map(obj: ArticleInfoDo) = with(obj) {
        ArticleInfo (
            id = id,
            authorId = authorId,
            title = title,
            content = content,
            cover = cover,
            label = label,
            type = type,
            cTime = cTime,
            uTime = uTime,
        )
    }

    private fun map(obj: ArticleInfo) = with(obj) {
        ArticleInfoDo(
            id, authorId, title, content, cover, label, type, cTime, uTime
        )
    }

}


