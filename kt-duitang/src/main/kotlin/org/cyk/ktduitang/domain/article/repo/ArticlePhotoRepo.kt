package org.cyk.ktduitang.domain.article.repo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.springframework.stereotype.Component

@TableName(value = "article_photo")
class ArticlePhotoDo (
    @TableId
    val id: Long, // 文章 id
    val aid: Long,
    val photoPath: String, // 图片路径
    val sort: Long, // 图片展示顺序(由小到大)
)

@Component
class ArticlePhotoRepo(
    val databaseTemplate: DatabaseTemplate<ArticleInfoDo, Long>
) {

}