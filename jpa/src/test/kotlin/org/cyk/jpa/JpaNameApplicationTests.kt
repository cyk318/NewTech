package org.cyk.jpa

import org.cyk.jpa.repo.UserRepoJpql
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class JpaNameApplicationTests {

    @Resource
    private lateinit var userRepoJpql: UserRepoJpql

    @Test
    fun test1() {
        val result = userRepoJpql.findByCond1("cyk", 21)
        result.forEach(::println)
    }

    fun test2() {

    }


}
