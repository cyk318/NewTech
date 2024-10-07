package org.cyk.ktearth.facade.user

import jakarta.validation.Valid
import org.cyk.ktearth.application.user.AdminAuditAvatarHandler
import org.cyk.ktearth.application.user.AuditAvatarCmd
import org.cyk.ktearth.infra.model.ApiResp
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员/用户
 * @author yikang.chen
 */
@RestController
@RequestMapping("/admin/audit")
class AdminAuditApi(
    private val adminAuditAvatarHandler: AdminAuditAvatarHandler,
) {

    /**
     * 头像审核
     */
    @PostMapping("/avatar")
    fun avatar(
        @RequestBody @Valid dto: AuditAvatarDto,
    ): ApiResp<Unit> {
        val cmd = AuditAvatarCmd (
            ok = dto.ok,
            userId = dto.userId
        )
        adminAuditAvatarHandler.handler(cmd)
        return ApiResp.ok()
    }

}

data class AuditAvatarDto (
    val userId: String,
    val ok: Boolean
)

