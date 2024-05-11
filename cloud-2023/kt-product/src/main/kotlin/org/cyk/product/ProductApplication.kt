package org.cyk.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication(scanBasePackages = ["org.cyk"])
class ProductApplication

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
