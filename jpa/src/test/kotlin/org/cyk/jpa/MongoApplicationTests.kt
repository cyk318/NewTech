package org.cyk.jpa

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import jakarta.annotation.Resource
import org.cyk.jpa.model.Album
import org.springframework.data.mongodb.core.MongoTemplate

@SpringBootTest
class MongoApplicationTests {

    @Resource
    private lateinit var mongoTemplate: MongoTemplate

    @Test
    fun test() {
        val obj = Album(
            title = "今天天气真好",
            content = "今天天气真好, 我要出去玩游戏",
            state = 1,
            albumNo = 1,
        )
        val a = mongoTemplate.save(obj)
        println(a)
    }

}
