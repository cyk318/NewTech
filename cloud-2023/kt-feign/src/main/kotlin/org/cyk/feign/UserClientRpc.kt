package org.cyk.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("user")
interface UserClientRpc {

    @GetMapping("/user/test")
    fun test(): String

}