package org.cyk.ktearth.facade.article

import jakarta.validation.constraints.Min
import org.cyk.ktearth.application.article.ArticlePageCmd
import org.cyk.ktearth.application.article.ArticlePageHandler
import org.cyk.ktearth.application.article.ArticlePageVo
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.model.PageResp
import org.hibernate.validator.constraints.Range
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 文章
 */
@Validated
@RestController
@RequestMapping("/article/info")
class ArticleInfoApi(
    private val articlePageHandler: ArticlePageHandler,
) {

    /**
     * 分页查询
     */
    @GetMapping("/page")
    fun page(
        @RequestParam(required = false, defaultValue = "0") @Min(0) start: Int,
        @RequestParam(required = false, defaultValue = "24") @Range(min = 1, max = 24) limit: Int,
        @RequestParam(required = false) label: List<String>?,
    ): ApiResp<PageResp<ArticlePageVo>> {
        val cmd = ArticlePageCmd (
            start = start,
            limit = limit,
            label = label ?: emptyList()
        )
        val pageVos = articlePageHandler.handler(cmd)
        return ApiResp.ok(pageVos)
    }

}



