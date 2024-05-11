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
class SuggestTests {

    @Resource
    private lateinit var elasticsearchTemplate: ElasticsearchTemplate


}
