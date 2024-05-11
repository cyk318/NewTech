package org.cyk.es

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders
import co.elastic.clients.elasticsearch.transform.ElasticsearchTransformClient
import co.elastic.clients.json.JsonData
import jakarta.annotation.Resource
import org.cyk.es.model.AlbumInfoDo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder
import org.springframework.data.elasticsearch.core.document.Document
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.data.elasticsearch.core.query.UpdateQuery
import java.lang.annotation.Native

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

}
