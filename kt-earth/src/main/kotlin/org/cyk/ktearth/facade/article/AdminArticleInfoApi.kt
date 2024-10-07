package org.cyk.ktearth.facade.article

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import org.cyk.ktearth.application.article.AdminArticlePubCmd
import org.cyk.ktearth.application.article.AdminArticlePubHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.hibernate.validator.constraints.Length
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import kotlin.math.max

/**
 * 文章
 */
@RestController
@RequestMapping("/admin/article/info")
class AdminArticleInfoApi(
    private val adminArticlePubHandler: AdminArticlePubHandler,
) {

    /**
     * 发布
     */
    @PostMapping("/publish")
    fun publish(
        request: HttpServletRequest,
        @RequestBody @Valid dto: AdminArticlePubDto,
    ): ApiResp<Unit> {
        val cmd = with(dto) {
            AdminArticlePubCmd(
                authorId = UserTokenUtils.getUserIdByRequest(request),
                title = title,
                content = content,
                cover = cover,
                label = label,
                type = type
            )
        }
        adminArticlePubHandler.handler(cmd)
        return ApiResp.ok()
    }

}

data class AdminArticlePubDto(
    @field:Length(min = 2, max = 88)
    val title: String,
    @field:Length(min = 10, max = 10_0000)
    val content: String,
    val cover: MultipartFile,
    @field:Size(min = 1, max = 5)
    @field:Valid
    val label: List<@Length(min = 1, max = 16) String>,
    val type: Int,
)


