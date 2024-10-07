package org.cyk.ktearth.domain.article.domain

import java.util.Date

data class ArticleInfo (
    var id: String? = null,
    val authorId: String,
    val title: String,
    val content: String,
    val cover: String, //文章封面
    val label: List<String>, //标签
    val type: ArticleType = ArticleType.NORMAL, //文章类型
    val cTime: Long = Date().time,
    val uTime: Long = Date().time,
)

enum class ArticleType(
    val code: Int,
    val msg: String,
) {
    BAN(-1, "封禁"),
    NORMAL(0, "原创"),
    DRAFT(1, "草稿"),
    // REVIEW(2), //文章暂不考虑审核
    ;
    companion object {
        fun of(code: Int) = entries.firstOrNull { it.code == code }
    }
}
