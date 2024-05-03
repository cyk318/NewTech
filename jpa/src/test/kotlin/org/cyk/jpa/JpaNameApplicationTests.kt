package org.cyk.jpa

import org.cyk.jpa.repo.UserRepoName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class JpaNameApplicationTests {

    @Resource
    private lateinit var userRepoName: UserRepoName

    @Test
    fun test1() {
        userRepoName.findByUsername("cyk").forEach(::println)
    }

    @Test
    fun test2() {
        userRepoName.findByUsernameLike("%y%").forEach(::println)
    }

    @Test
    fun test3() {
        userRepoName.findByUsernameAndAge("cyk", 21).forEach(::println)
    }

    @Test
    fun test4() {
        userRepoName.findByIdLessThanEqual(2).forEach(::println)
    }

    @Test
    fun test5() {
        userRepoName.findByIdBetween(1, 3).forEach(::println)
    }

    @Test
    fun test6() {
        userRepoName.findByIdIn(listOf(1,2)).forEach(::println)
    }

}
