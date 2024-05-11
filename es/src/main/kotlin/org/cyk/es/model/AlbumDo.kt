package org.cyk.es.model
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "album_list")
data class AlbumListDo(
    @Id
    @Field(type = FieldType.Keyword)
    var id: Long,
    @Field(type = FieldType.Nested) // 表示一个嵌套结构
    var userinfo: UserInfoSimp,
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    var title: String,
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    var content: String,
    @Field(type = FieldType.Nested) // 表示一个嵌套结构
    var photos: List<AlbumPhotoSimp>,
)

data class UserInfoSimp(
    @Field(type = FieldType.Long)
    val userId: Long,
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    val username: String,
    @Field(type = FieldType.Keyword, index = false)
    val avatar: String,
)

data class AlbumPhotoSimp(
    @Field(type = FieldType.Integer, index = false)
    val sort: Int,
    @Field(type = FieldType.Keyword, index = false)
    val photo: String,
)
