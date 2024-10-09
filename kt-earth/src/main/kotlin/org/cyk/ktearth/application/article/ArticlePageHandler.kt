package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.domain.ArticleInfo
import org.cyk.ktearth.domain.article.domain.ArticleStat
import org.cyk.ktearth.domain.article.domain.ArticleType
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.article.repo.ArticleStatRepo
import org.cyk.ktearth.domain.user.domain.UserInfo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.model.PageResp
import org.springframework.stereotype.Component

data class ArticlePageCmd (
    val start: Int,
    val limit: Int,
    val label: List<String>,
)

@Component
class ArticlePageHandler(
    private val userInfoRepo: UserInfoRepo,
    private val articleInfoRepo: ArticleInfoRepo,
    private val articleStatRepo: ArticleStatRepo,
): ApplicationHandler<ArticlePageCmd, PageResp<ArticlePageVo>> {

    override fun handler(cmd: ArticlePageCmd): PageResp<ArticlePageVo> {
        val pageRes = articleInfoRepo.page(cmd)

        // 文章信息
        val articles = pageRes.result
        // 统计信息
        val articleIds = articles.map { it.id!! }
        val articleStatMap = articleStatRepo.queryByArticleIds(articleIds).groupBy { it.articleId }.mapValues { it.value[0] }
        // 用户信息
        val userIds = articles.map { it.authorId }
        val userInfoMap = userInfoRepo.queryByIds(userIds).groupBy { it.id!! }.mapValues { it.value[0] }

        return PageResp.ok(
            pageRes.hasMore,
            pageRes.nextStart,
            map(articles, articleStatMap, userInfoMap),
            pageRes.total,
        )
    }

    private fun map(
        articles: List<ArticleInfo>,
        articleStatMap: Map<String, ArticleStat>,
        userInfoMap: Map<String, UserInfo>,
    ): List<ArticlePageVo> {
        return articles.map {
            ArticlePageVo(
                id = it.id!!,
                title = it.title,
                content = it.content,
                cover = it.cover,
                label = it.label,
                type = it.type.code,
                cTime = it.cTime,
                uTime = it.uTime,
                author = with(userInfoMap[it.authorId]!!) {
                    ArticlePageVo.UserTinyVo(
                        id = id!!,
                        username = username,
                        avatar = avatar ?: "",

                    )
                },
                stat = with(articleStatMap[it.id]!!) {
                    ArticlePageVo.ArticleStatVo(
                        likeCnt = likeCnt,
                        viewCnt = viewCnt,
                        collectCnt = collectCnt,
                        commentCnt = commentCnt,
                    )
                }
            )
        }
    }


}

data class ArticlePageVo (
    var id: String,
    val title: String,
    val content: String,
    val cover: String,
    val label: List<String>,
    val type: Int,
    val cTime: Long,
    val uTime: Long,

    val author: UserTinyVo,
    val stat: ArticleStatVo,
) {
    data class UserTinyVo (
        var id: String,
        val username: String,
        var avatar: String,
    )
    data class ArticleStatVo (
        val likeCnt: Long,
        val viewCnt: Long,
        val collectCnt: Long,
        val commentCnt: Long,
    )
}

