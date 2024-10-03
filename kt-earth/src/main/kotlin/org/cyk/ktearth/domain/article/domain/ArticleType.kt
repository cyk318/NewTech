package org.cyk.ktearth.domain.article.domain

enum class ArticleType(
    val code: Int
) {
    NORMAL(0),
    BAN(1), //封禁
    REVIEW(2), //文章暂不考虑审核
    ;
    companion object {
        fun getType(code: Int) = entries.firstOrNull { it.code == code }
    }
}
