package org.cyk.ktduitang.domain.article.repo

import org.springframework.stereotype.Component

class ArticlePhotoDo (
    val id: Long, // 文章 id
    val aid: Long,
    val photoPath: String, // 图片路径
    val sort: Long, // 图片展示顺序(由小到大)
)

@Component
class ArticlePhotoRepo(
) {

}