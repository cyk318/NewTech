package org.cyk.ktduitang.facade.api

import jakarta.validation.Valid
import org.cyk.ktduitang.application.UserinfoService
import org.cyk.ktduitang.facade.model.LoginDto
import org.cyk.ktduitang.facade.model.RegDto
import org.cyk.ktduitang.infra.config.ApiResp
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/info/")
class UserAuthApi(
    private val userInfoService: UserinfoService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid dto: LoginDto
    ): ApiResp<String> {
        val result = userInfoService.login(dto)
        return ApiResp.ok(result)
    }

    @PostMapping("/reg")
    fun reg(
        @RequestBody @Valid dto: RegDto,
    ): ApiResp<Int> {
        userInfoService.reg(dto)
        return ApiResp.ok(1)
    }

}


