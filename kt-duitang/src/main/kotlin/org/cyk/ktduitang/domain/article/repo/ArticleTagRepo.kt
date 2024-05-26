package org.cyk.ktduitang.domain.article.repo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.springframework.stereotype.Component
import java.util.Date

@TableName(value = "article_tag")
data class ArticleTag (
    @TableId
    val id: Long,
    val aId: Long,
    val uId: Long,
    val name: String,
    val cTime: Date,
)

@Component
class ArticleTagRepo {

}