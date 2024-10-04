package org.cyk.ktearth.facade.admin

import org.cyk.ktearth.application.user.AdminRegCmd
import org.cyk.ktearth.application.user.AdminRegHandler
import org.hibernate.validator.constraints.Length
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 管理员权限信息
 * @author yikang.chen
 */
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
        @RequestBody regDto: RegDto,
    ) {
        val cmd = AdminRegCmd (regDto.username)
        adminRegHandler.handler(cmd)
    }

}

data class RegDto (
    @field:Length(min = 2, max = 32)
    val username: String,
)

