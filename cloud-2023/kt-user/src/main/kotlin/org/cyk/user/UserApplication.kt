package org.cyk.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients


@SpringBootApplication(scanBasePackages = ["org.cyk"])
@EnableFeignClients(basePackages = ["org.cyk"])
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}
