package org.cyk.jpa.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collation = "album")
@CompoundIndexes(
    CompoundIndex(name = "idx_id_state", def = "{_id: 1, state: 1}", background = true),
    CompoundIndex(name = "idx_id_title", def = "{content: 1, title: 1}", background = true)
)
data class Album (
    @Id
    val id: String? = null,
    val title: String,
    val content: String,
    val state: Int,
    @Indexed(name = "uniq_ano", unique = true, background = true)
    val albumNo: Long,
)


