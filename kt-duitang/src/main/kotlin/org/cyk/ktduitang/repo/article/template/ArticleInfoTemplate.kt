package org.cyk.ktduitang.repo.article.template

import org.cyk.ktduitang.repo.article.ArticleInfoDo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


@Repository
interface ArticleInfoTemplate: JpaRepository<ArticleInfoDo, Long>, JpaSpecificationExecutor<ArticleInfoDo>