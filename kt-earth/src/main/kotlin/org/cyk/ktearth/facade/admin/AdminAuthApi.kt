package org.cyk.ktearth.facade.admin

import jakarta.validation.constraints.NotBlank
import org.cyk.ktearth.application.user.AdminRegCmd
import org.cyk.ktearth.application.user.AdminRegHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/auth")
class AdminAuthApi(
    private val adminRegHandler: AdminRegHandler,
) {

    @PostMapping("/reg")
    fun reg(
        @RequestBody regDto: RegDto,
    ) {
        val cmd = AdminRegCmd (regDto.username)
        adminRegHandler.handler(cmd)
    }

}

data class RegDto (
    @field:NotBlank
    val username: String,
)

