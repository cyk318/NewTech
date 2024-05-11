package org.cyk.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("kt-user")
interface UserClientRpc {

    @GetMapping("/test")
    fun test(): String

}