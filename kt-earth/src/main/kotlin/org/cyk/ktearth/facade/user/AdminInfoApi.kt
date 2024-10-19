package org.cyk.ktearth.facade.user

import org.cyk.ktearth.application.user.AdminPageInfoCmd
import org.cyk.ktearth.application.user.AdminPageInfoHandler
import org.cyk.ktearth.application.user.AdminPageInfoVo
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.model.PageResp
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员/用户
 */
@RestController
@RequestMapping("/admin/info")
class AdminInfoApi(
    private val adminPageInfoHandler: AdminPageInfoHandler,
) {

    /**
     * 分页查询
     */
    @GetMapping("/page")
    fun page(
        @RequestParam(required = false, defaultValue = "0") start: Int,
        @RequestParam(required = false, defaultValue = "24") limit: Int,
        @RequestParam(required = false) id: String?,
        @RequestParam(required = false) username: String?,
        @RequestParam(required = false) auth: Int?,
    ): ApiResp<PageResp<AdminPageInfoVo>> {
        val cmd = AdminPageInfoCmd(start, limit, id, username, auth)
        val vo = adminPageInfoHandler.handler(cmd)
        return ApiResp.ok(vo)
    }

}
