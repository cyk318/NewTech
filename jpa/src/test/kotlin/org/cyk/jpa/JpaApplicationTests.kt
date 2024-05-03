package org.cyk.jpa

import org.cyk.jpa.model.Userinfo
import org.cyk.jpa.repo.UserRepoJpa
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class JpaApplicationTests {

    @Resource
    private lateinit var userRepoJpa: UserRepoJpa

    @Test
    fun test() {
        val obj = Userinfo(
            username = "cyk2",
            age = 21,
        )
        userRepoJpa.save(obj)
        val result = userRepoJpa.findAll()
        result.forEach(::println)
    }

}
