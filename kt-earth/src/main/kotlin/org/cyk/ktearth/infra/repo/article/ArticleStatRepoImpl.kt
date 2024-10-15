package org.cyk.ktearth.infra.repo.article
import org.cyk.ktearth.domain.article.domain.ArticleStat
import org.cyk.ktearth.domain.article.repo.ArticleStatRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

@Document("article_stat")
data class ArticleStatDo (
    @Id
    val articleId: String,
    val likeCnt: Long,
    val viewCnt: Long,
    val collectCnt: Long,
    val commentCnt: Long,
    val cTime: Long,
    val uTime: Long,
)

@Repository
class ArticleStatRepoImpl(
    private val mongoTemplate: MongoTemplate,
): ArticleStatRepo {

    override fun save(o: ArticleStat) {
        val obj = map(o)
        mongoTemplate.save(obj)
    }

    override fun queryByArticleIds(articleIds: List<String>): List<ArticleStat> {
        val c = Criteria.where("_id").`in`(articleIds)
        return mongoTemplate.find(Query.query(c), ArticleStatDo::class.java).map { map(it) }
    }

    override fun queryByArticleId(articleId: String): ArticleStat? {
        val c = Criteria.where("_id").`in`(articleId)
        return mongoTemplate.findOne(Query.query(c), ArticleStatDo::class.java)?.let { map(it) }
    }

    private fun map(obj: ArticleStatDo) = with(obj) {
        ArticleStat(articleId, likeCnt,viewCnt, collectCnt, commentCnt, Date(cTime), Date(uTime))
    }

    private fun map(bo: ArticleStat) = with(bo) {
        ArticleStatDo(
            articleId, likeCnt, viewCnt, collectCnt, commentCnt, cTime.time, uTime.time
        )
    }

}


