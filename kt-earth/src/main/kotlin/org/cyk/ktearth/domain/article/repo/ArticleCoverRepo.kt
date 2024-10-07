package org.cyk.ktearth.domain.article.repo

import org.springframework.web.multipart.MultipartFile

interface ArticleCoverRepo {

    fun save(cover: MultipartFile): String

    fun delByCover(cover: String)

}