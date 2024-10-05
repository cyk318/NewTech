package org.cyk.ktearth.facade.user

import jakarta.validation.Valid
import org.cyk.ktearth.application.user.AdminRegCmd
import org.cyk.ktearth.application.user.AdminRegHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员
 * @author yikang.chen
 */
@Validated
@RestController
@RequestMapping("/admin/auth")
class AdminAuthApi(
    private val adminRegHandler: AdminRegHandler,
) {

    /**
     * 注册
     */
    @PostMapping("/reg")
    fun reg(
        @RequestBody @Valid regDto: RegDto,
    ): ApiResp<Unit> {
        val cmd = AdminRegCmd (regDto.username)
        adminRegHandler.handler(cmd)
        return ApiResp.ok()
    }

}

data class RegDto (
    @field:Length(min = 2, max = 32)
    val username: String,
)

