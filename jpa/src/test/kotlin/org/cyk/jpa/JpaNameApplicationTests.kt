package org.cyk.jpa

import org.cyk.jpa.model.Userinfo
import org.cyk.jpa.repo.UserRepoJpql
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import javax.annotation.Resource

@SpringBootTest
class JpaNameApplicationTests {

    @Resource
    private lateinit var userRepoJpql: UserRepoJpql

    @Test
    fun test1() {
        userRepoJpql.findByCond1("cyk", 21).forEach(::println)
    }

    @Test
    fun test2() {
        userRepoJpql.findByCond2("cyk", 21).forEach(::println)
    }

    @Test
    fun test3() {
        userRepoJpql.findByCond3("y").forEach(::println)
    }

    @Test
    fun test4() {
        val pg = PageRequest.of(1, 2)
        userRepoJpql.findByCond4(pg, "y").forEach(::println)
    }

    @Test
    fun test5() {
        userRepoJpql.findByCond5(listOf(21,22,23)).forEach(::println)
    }

    @Test
    fun test6() {
        userRepoJpql.findByCond6(
            Userinfo(
                username = "cyk",
                age = 21
            )
        ).forEach(::println)
    }

}
