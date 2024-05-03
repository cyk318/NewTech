package org.cyk.jpa

import org.cyk.jpa.model.Userinfo
import org.cyk.jpa.repo.UserRepoJpa
import org.cyk.jpa.repo.UserRepoSql
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class JpaSqlApplicationTests {

    @Resource
    private lateinit var userRepoSql: UserRepoSql

    @Test
    fun test() {
        userRepoSql.findByCond("cyk", 21).forEach(::println)
    }

}
