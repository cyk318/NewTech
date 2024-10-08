package org.cyk.ktearth.facade.article

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.cyk.ktearth.application.article.*
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.hibernate.validator.constraints.Length
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * 管理员/文章
 */
@RestController
@RequestMapping("/admin/article/info")
class AdminArticleInfoApi(
    private val adminArticlePubHandler: AdminArticlePubHandler,
    private val adminArticleRemoveHandler: AdminArticleRemoveHandler,
    private val adminArticleUpdateHandler: AdminArticleUpdateHandler,
) {

    /**
     * 发布
     */
    @PostMapping("/publish")
    fun publish(
        request: HttpServletRequest,
        @ModelAttribute @Valid dto: AdminArticlePubDto,
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

    /**
     * 删除
     */
    @PostMapping("/remove")
    fun remove(
        @RequestBody dto: AdminArticleRemoveDto,
    ): ApiResp<Unit> {
        val cmd = with(dto)  {
            AdminArticleRemoveCmd (
                articleId = articleId
            )
        }
        adminArticleRemoveHandler.handler(cmd)
        return ApiResp.ok()
    }

    /**
     * 更新 | 实时更新
     */
    @PostMapping("/update")
    fun update(
        request: HttpServletRequest,
        @ModelAttribute @Valid dto: AdminArticleUpdateDto,
    ): ApiResp<Unit> {
        val cmd = with(dto) {
            AdminArticleUpdateCmd (
                UserTokenUtils.getUserIdByRequest(request),
                id, authorId, title, content, cover, label, type)
        }
        adminArticleUpdateHandler.handler(cmd)
        return ApiResp.ok()
    }

}

data class AdminArticleUpdateDto(
    @field:NotBlank
    val id: String,
    @field:NotBlank
    val authorId: String,
    @field:Length(min = 2, max = 88)
    val title: String,
    @field:Length(min = 10, max = 10_0000)
    val content: String,
    @field:NotNull
    val cover: MultipartFile,
    @field:Size(min = 1, max = 5)
    @field:Valid
    val label: List<@Length(min = 1, max = 16) String>,
    /**
     * 0原创 1草稿
     */
    @field:NotNull
    val type: Int,
)

data class AdminArticlePubDto(
    @field:Length(min = 2, max = 88)
    val title: String,
    @field:Length(min = 10, max = 10_0000)
    val content: String,
    @field:NotNull
    val cover: MultipartFile,
    @field:Size(min = 1, max = 5)
    @field:Valid
    val label: List<@Length(min = 1, max = 16) String>,
    /**
     * 0原创 1草稿
     */
    @field:NotNull
    val type: Int,
)

data class AdminArticleRemoveDto(
    @NotBlank
    val articleId: String,
)

