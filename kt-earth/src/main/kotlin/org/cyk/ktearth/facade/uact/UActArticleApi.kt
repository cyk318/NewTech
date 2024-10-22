package org.cyk.ktearth.facade.uact

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.NotBlank
import org.cyk.ktearth.application.uact.LikePostCmd
import org.cyk.ktearth.application.uact.LikePostHandler
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
import kotlin.io.path.fileVisitor
import kotlin.reflect.typeOf

/**
 * 用户行为
 */
@RestController
@RequestMapping("/uact")
class UActArticleApi(
    private val viewPostHandler: ViewPostHandler,
    private val flowLimitService: FlowLimitService,
    private val likePostHandler: LikePostHandler,
) {

    /**
     * 访问量
     */
    @PostMapping("/article/view")
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

    /**
     * 点赞/取消点赞
     */
    @PostMapping("/like")
    fun like(
        request: HttpServletRequest,
        @RequestBody dto: LikePostDto
    ): ApiResp<Int> {
        val cmd = LikePostCmd(
            userId = UserTokenUtils.getUserIdByRequest(request),
            targetId = dto.targetId,
            type = dto.type
        )
        val likeStatus = likePostHandler.handler(cmd)
        return ApiResp.ok(likeStatus)
    }

}

data class LikePostDto(
    val targetId: String,
    val type: Int,
)

data class ViewPostDto(
    val targetId: String,
)

