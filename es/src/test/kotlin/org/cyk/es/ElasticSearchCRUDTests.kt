package org.cyk.es

import jakarta.annotation.Resource
import org.cyk.es.model.AlbumInfoDo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.document.Document
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.UpdateQuery

@SpringBootTest
class ElasticSearchCRUDTests {

    @Resource
    private lateinit var elasticsearchTemplate: ElasticsearchTemplate

    @Test
    fun testSave() {
        //保存单条数据
        val a1 = AlbumInfoDo(
            id = 1,
            userId = 10000,
            title = "今天天气真好",
            content = "学习完之后，我要出去好好玩"
        )
        val result = elasticsearchTemplate.save(a1)
        println(result)

        //保存多条数据
        val list = listOf(
            AlbumInfoDo(2, 10000, "西安六号线避雷", "前俯后仰。他就一直在那前后动。他背后是我朋友，我让他不要挤了，他直接就急了，开始故意很大力的挤来挤去。"),
            AlbumInfoDo(3, 10000, "字节跳动快上车～", "#内推 #字节跳动内推 #互联网"),
            AlbumInfoDo(4, 10000, "连王思聪也变得低调老实了", "如今的王思聪，不仅交女友的质量下降，在网上也不再像以前那样随意喷这喷那。显然，资金的紧张让他低调了许多")
        )
        val resultList = elasticsearchTemplate.save(list)
        resultList.forEach(::println)
    }

    @Test
    fun testDelete() {
        //根据主键删除，例如删除主键 id = 1 的文档
        elasticsearchTemplate.delete("1", AlbumInfoDo::class.java)
    }

    @Test
    fun testGet() {
        //根据主键获取文档
        val result = elasticsearchTemplate.get("1", AlbumInfoDo::class.java)
        println(result)
    }

    @Test
    fun testUpdate() {
        //例如，修改 id = 1 的文档
        val id = 1
        val title = "今天天气不太好"
        val content = "天气不好，只能在家里学习了。。。"

        val uq = UpdateQuery.builder(id.toString())
            .withDocument(
                Document.create()
                    .append("title", title)
                    .append("content", content)
            ).build()

        val result = elasticsearchTemplate.update(uq, IndexCoordinates.of("album_info")).result
        println(result.ordinal)
        println(result.name)
    }

}
