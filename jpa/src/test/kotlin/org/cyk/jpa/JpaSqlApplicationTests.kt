package org.cyk.jpa

import org.cyk.jpa.repo.UserRepoSql
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import jakarta.annotation.Resource

@SpringBootTest
class JpaSqlApplicationTests {

    @Resource
    private lateinit var userRepoSql: UserRepoSql

    @Test
    fun test() {
        userRepoSql.findByCond("cyk", 21).forEach(::println)
    }

}
