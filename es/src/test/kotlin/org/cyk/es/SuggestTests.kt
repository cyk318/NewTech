package org.cyk.es

import co.elastic.clients.elasticsearch.core.search.FieldSuggester
import co.elastic.clients.elasticsearch.core.search.FieldSuggesterBuilders
import co.elastic.clients.elasticsearch.core.search.Suggester
import jakarta.annotation.Resource
import org.cyk.es.model.AlbumSugDo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.suggest.response.Suggest

@SpringBootTest
class SuggestTests {

    @Resource
    private lateinit var elasticsearchTemplate: ElasticsearchTemplate

    @Test
    fun init() {
        if(elasticsearchTemplate.indexOps(AlbumSugDo::class.java).exists()) {
            elasticsearchTemplate.indexOps(AlbumSugDo::class.java).delete()
        }
        elasticsearchTemplate.indexOps(AlbumSugDo::class.java).create()
        elasticsearchTemplate.indexOps(AlbumSugDo::class.java).putMapping(
            elasticsearchTemplate.indexOps(AlbumSugDo::class.java).createMapping()
        )
        elasticsearchTemplate.save(
            listOf(
                AlbumSugDo(1, 10000, "今天发现西安真美", "西安真美丽啊，来到了钟楼...."),
                AlbumSugDo(2, 10000, "今天六号线避雷", "前俯后仰。他就一直在那前后动。他背后是我朋友，我让他不要挤了，他直接就急了，开始故意很大力的挤来挤去。"),
                AlbumSugDo(3, 10000, "字节跳动快上车～", "#内推 #字节跳动内推 #互联网"),
                AlbumSugDo(4, 10000, "连王思聪也变得低调老实了", "如今的王思聪，不仅交女友的质量下降，在网上也不再像以前那样随意喷这喷那。显然，资金的紧张让他低调了许多")
            )
        )
    }

    @Test
    fun suggestTest() {
        //模拟客户端输入的需要自动补全的字段
        val input = "今天"
        val limit = 10

        val fieldSuggester = FieldSuggester.Builder()
            .text(input) //用户输入
            .completion(
                FieldSuggesterBuilders.completion()
                    .field("suggestion") //对哪个字段自动补全
                    .skipDuplicates(true) //如果有重复的词条，自动跳过
                    .size(limit) //最多显示 limit 条数据
                    .build()
            )
            .build()

        val query = NativeQuery.builder()
            .withSuggester(Suggester.of { s -> s.suggesters("sug1", fieldSuggester) }) //参数一: 自定义自动补全名
            .build()

        val hits = elasticsearchTemplate.search(query, AlbumSugDo::class.java)

        val suggestList = hits.suggest
            ?.getSuggestion("sug1")
            ?.entries?.get(0)
            ?.options?.map(::map) ?: emptyList()

        println(suggestList)
    }

    private fun map(hit: Suggest.Suggestion.Entry.Option): String {
        return hit.text
    }

}
