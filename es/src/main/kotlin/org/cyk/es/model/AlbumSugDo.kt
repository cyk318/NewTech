package org.cyk.es.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.CompletionField
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.core.suggest.Completion

@Document(indexName = "album_doc")
data class AlbumSugDo (
    @Id
    @Field(type = FieldType.Keyword)
    val id: Long,
    @Field(name = "user_id", type = FieldType.Long)
    val userId: Long,
    @Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = ["suggestion"]) //注意，copyTo 的字段一定是 var 类型
    val title: String,
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    val content: String,
    @CompletionField(maxInputLength = 100, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    var suggestion: Completion? = null, //注意，被 copyTo 的字段一定要是 var 类型
)
