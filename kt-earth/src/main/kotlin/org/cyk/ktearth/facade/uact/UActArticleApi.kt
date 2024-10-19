package org.cyk.ktearth.facade.uact

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.NotBlank
import org.cyk.ktearth.application.uact.ViewPostCmd
import org.cyk.ktearth.application.uact.ViewPostHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.cyk.ktearth.service.FlowLimit
import org.cyk.ktearth.service.FlowLimitService
import org.cyk.ktearth.service.LimitType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 用户行为/文章相关
 */
@RestController
@RequestMapping("/uact/article")
class UActArticleApi(
    private val viewPostHandler: ViewPostHandler,
    private val flowLimitService: FlowLimitService,
) {

    /**
     * 访问量
     */
    @PostMapping("/view")
    fun viewPost(
        request: HttpServletRequest,
        @RequestBody dto: ViewPostDto
    ): ApiResp<Unit> {
        val cmd = ViewPostCmd (
            postId = UserTokenUtils.getUserIdByRequest(request),
            targetId = dto.targetId,
        )
        flowLimitService.entry(FlowLimit(
            type = LimitType.VIEW_POST,
            postId = cmd.postId,
            targetId = cmd.targetId
        ))

        viewPostHandler.handler(cmd)
        return ApiResp.ok()
    }

    @PostMapping("/like")
    fun like() {

    }

}

data class LikePostDto(
    val targetId: String,
)

data class ViewPostDto(
    val targetId: String,
)

