package org.cyk.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackages = ["org.cyk"])
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}
