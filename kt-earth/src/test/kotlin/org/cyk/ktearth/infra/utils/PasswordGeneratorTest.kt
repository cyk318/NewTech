package org.cyk.ktearth.infra.utils

import kotlin.test.Test

class PasswordGeneratorTest {

    @Test
    fun test() {
        val result = PasswordGenerator.generate()
        println(result)
        println(result.length)
    }


}