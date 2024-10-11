package org.cyk.ktearth.facade.uact

import jakarta.servlet.http.HttpServletRequest
import org.cyk.ktearth.application.uact.ViewPostCmd
import org.cyk.ktearth.application.uact.ViewPostHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uact/view")
class ViewApi(
    private val viewPostHandler: ViewPostHandler,
) {

    @PostMapping
    fun post(
        request: HttpServletRequest,
        @RequestBody dto: ViewPostDto
    ): ApiResp<Unit> {
        val cmd = ViewPostCmd (
            targetId = dto.targetId,
            postId = UserTokenUtils.getUserIdByRequest(request),
        )
        viewPostHandler.handler(cmd)
        return ApiResp.ok()
    }

}

data class ViewPostDto (
    val targetId: String,
)

