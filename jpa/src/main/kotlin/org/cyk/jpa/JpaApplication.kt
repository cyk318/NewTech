package org.cyk.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing //用来开启 JPA 审计功能
@SpringBootApplication
class JpaApplication

fun main(args: Array<String>) {
    runApplication<JpaApplication>(*args)
}
