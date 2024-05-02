package org.cyk.jpa

import org.cyk.jpa.model.Userinfo
import org.cyk.jpa.repo.UserRepo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class JpaApplicationTests {

    @Resource
    private lateinit var userRepo: UserRepo

    @Test
    fun test() {
        val obj = Userinfo(
            username = "cyk3",
            age = 21,
        )
        userRepo.save(obj)
        val result = userRepo.findAll()
        result.forEach(::println)
    }

}
