package org.cyk.es.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "album_info", )
data class AlbumInfoDo (
    /**
     * @Id: 表示文档中的主键，并且会在保存在 ElasticSearch 数据结构中 {"id": "", "userId": "", "title": ""}
     */
    @Id
    @Field(type = FieldType.Keyword)
    val id: Long? = null,
    /**
     * @Field: 描述 Java 类型中的属性映射
     *      - name: 对应 ES 索引中的字段名. 默认和属性同名
     *      - type: 对应字段类型，默认是 FieldType.Auto (会根据我们数据类型自动进行定义)，但是建议主动定义，避免导致错误映射
     *      - index: 是否创建索引. text 类型创建倒排索引，其他类型创建正排索引.  默认是 true
     *      - analyzer: 分词器名称.  中文我们一般都使用 ik 分词器(ik分词器有 ik_smart 和 ik_max_word)
     */
    @Field(name = "user_id", type = FieldType.Long)
    val userId: Long,
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    var title: String,
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    var content: String,
)


