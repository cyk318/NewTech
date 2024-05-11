package org.cyk.es

import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.json.JsonData
import jakarta.annotation.Resource
import org.cyk.es.model.AlbumInfoDo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.query.HighlightQuery
import org.springframework.data.elasticsearch.core.query.highlight.Highlight
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters

@SpringBootTest
class SearchTests {

    @Resource
    private lateinit var elasticsearchTemplate: ElasticsearchTemplate

    /**
     * 全文检索查询(match_all)
     */
    @Test
    fun testMatchAllQuery() {
        val query = NativeQuery.builder()
            .withQuery { q -> q
                .matchAll { it }
            }.build()

        val hits = elasticsearchTemplate.search(query, AlbumInfoDo::class.java)
        hits.forEach { println(it.content) }
    }

    /**
     * 精确查询(match)
     */
    @Test
    fun testMatchQuery() {
        val query = NativeQuery.builder()
            .withQuery { q -> q
                .match {
                    it.field("title").query("天气")
                }
            }.build()

        val hits = elasticsearchTemplate.search(query, AlbumInfoDo::class.java)
        hits.forEach { println(it.content) }
    }

    /**
     * 精确查询(term)
     */
    @Test
    fun testTerm() {
        val query = NativeQuery.builder()
            .withQuery { q -> q
                .term { t -> t
                    .field("id").value("2")
                }
            }.build()

        val hits = elasticsearchTemplate.search(query, AlbumInfoDo::class.java)
        hits.forEach { println(it.content) }
    }

    /**
     * 范围搜索
     */
    @Test
    fun testRangeQuery() {
        val query = NativeQuery.builder()
            .withQuery { q -> q
                .range { r -> r
                    .field("id").gte(JsonData.of(1)).lt(JsonData.of(4)) // 大于等于 1，小于 4
                }
            }.build()
        val hits = elasticsearchTemplate.search(query, AlbumInfoDo::class.java)
        hits.forEach { println(it.content) }
    }

    /**
     * bool 复合搜索
     */
    @Test
    fun testBoolQuery() {
        val query = NativeQuery.builder()
            .withQuery { q -> q
                .bool { b -> b
                    .must { m -> m
                        .range { r -> r
                            .field("id").gte(JsonData.of(1)).lt(JsonData.of(4)) // 大于等于 1，小于 4
                        }
                    }
                    .mustNot { n -> n
                        .match { mc -> mc
                            mc.field("title").query("天气")
                        }
                    }
                    .should { s -> s
                        .matchAll { it }
                    }
                }
            }.build()
        val hits = elasticsearchTemplate.search(query, AlbumInfoDo::class.java)
        hits.forEach { println(it.content) }
    }

    /**
     * 排序 + 分页
     */
    @Test
    fun testSortAndPage() {
        //a) 方式一
//        val query = NativeQuery.builder()
//            .withQuery { q -> q
//                .matchAll { it }
//            }
//            .withPageable(
//                PageRequest.of(0, 3) //页码(从 0 开始)，非偏移量
//                    .withSort(Sort.by(Sort.Order.desc("id")))
//            ).build()

        //b) 方式二
        val query = NativeQuery.builder()
            .withQuery { q -> q
                .matchAll { it }
            }
            .withSort { s -> s.field { f->f.field("id").order(SortOrder.Desc) } }
            .withPageable(PageRequest.of(0, 3)) //页码(从 0 开始)，非偏移量)
            .build()
        val hits = elasticsearchTemplate.search(query, AlbumInfoDo::class.java)
        hits.forEach { println(it.content) }
    }

    @Test
    fun testHighLight() {
        //所有需要高亮的字段
        val highField = listOf(
            HighlightField("title"),
            HighlightField("content")
        )
        val query = NativeQuery.builder()
            .withQuery { q ->
                q.multiMatch { ma -> ma
                    .fields("title", "content").query("天气")
                }
            }
            .withHighlightQuery(
                HighlightQuery(
                    Highlight(
                        HighlightParameters.builder()
                            .withPreTags("<span style='color:red'>") //前缀标签
                            .withPostTags("</span>") //后缀标签
                            .withFragmentSize(10) //高亮的片段长度(多少个几个字需要高亮，一般会设置的大一些，让匹配到的字段尽量都高亮)
                            .withNumberOfFragments(1) //高亮片段的数量
                            .build(),
                        highField
                    ),
                    String::class.java
                )
            ).build()

        val hits = elasticsearchTemplate.search(query, AlbumInfoDo::class.java)

        //hits.content 本身是没有高亮数据的，因此这里需要手动处理
        hits.forEach {
            val result = it.content
            //根据高亮字段名称，获取高亮数据集合
            val titleList = it.getHighlightField("title")
            val contentList = it.getHighlightField("content")
            if (titleList.size > 0) result.title = titleList[0]
            if (contentList.size > 0) result.content = contentList[0]
            println(result)
        }
    }

}
