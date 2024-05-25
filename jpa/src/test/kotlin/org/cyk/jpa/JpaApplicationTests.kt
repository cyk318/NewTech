package org.cyk.jpa

import org.cyk.jpa.model.Userinfo
import org.cyk.jpa.repo.UserRepoJpa
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import jakarta.annotation.Resource

@SpringBootTest
class JpaApplicationTests {

    @Resource
    private lateinit var userRepoJpa: UserRepoJpa

    @Test
    fun test() {
        val obj = Userinfo(
            username = "龙洋静",
            age = 20,
        )
        userRepoJpa.save(obj)
        val result = userRepoJpa.findAll()
        result.forEach(::println)
    }

}
