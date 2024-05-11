package org.cyk.es

import jakarta.annotation.Resource
import org.cyk.es.model.AlbumInfoDo
import org.cyk.es.model.AlbumListDo
import org.cyk.es.model.AlbumPhotoSimp
import org.cyk.es.model.UserInfoSimp
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate

@SpringBootTest
class ElasticSearchIndexTests {

    @Resource
    private lateinit var elasticsearchTemplate: ElasticsearchTemplate

    @Test
    fun test1() {
        //存在索引库就删除
        if (elasticsearchTemplate.indexOps(AlbumInfoDo::class.java).exists()) {
            elasticsearchTemplate.indexOps(AlbumInfoDo::class.java).delete()
        }
        //创建索引库
        elasticsearchTemplate.indexOps(AlbumInfoDo::class.java).create()
        //设置映射
        elasticsearchTemplate.indexOps(AlbumInfoDo::class.java).putMapping(
            elasticsearchTemplate.indexOps(AlbumInfoDo::class.java).createMapping()
        )
    }

    @Test
    fun test2() {
        val obj = AlbumListDo(
            id = 1,
            userinfo = UserInfoSimp(
                userId = 1,
                username = "cyk",
                avatar = "env-base:9200"
            ),
            title = "今天天气真好",
            content = "早上起来，我要好好学习，然去公园散步~",
            photos = listOf(
                AlbumPhotoSimp(1, "www.photo.com/aaa"),
                AlbumPhotoSimp(2, "www.photo.com/bbb")
            )
        )
        val result = elasticsearchTemplate.save(obj)
        println(result)
    }

}
