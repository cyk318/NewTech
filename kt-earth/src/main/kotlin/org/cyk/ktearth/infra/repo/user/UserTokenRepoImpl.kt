package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.domain.user.repo.UserTokenRepo
import org.cyk.ktearth.infra.model.constants.RedisConst
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.TimeUnit

@Document("user_token")
data class UserTokenDo (
    @Id
    val userId: String,
    var token: String,
    var expireDate: Date,
    val cTime: Date,
    var uTime: Date,
)

data class SaveUserTokenCmd (
    val token: String,
    val userId: String,
)

@Repository
class UserTokenRepoImpl(
    private val redisTemplate: StringRedisTemplate
): UserTokenRepo {

    override fun save(cmd: SaveUserTokenCmd) {
        redisTemplate.opsForValue().set(
            RedisConst.makeUserTokenKey(cmd.userId),
            cmd.token,
            30,
            TimeUnit.DAYS,
        )
    }

    override fun getTokenByUserId(userId: String): String? {
        return redisTemplate.opsForValue().get(RedisConst.makeUserTokenKey(userId))
    }

    override fun delByUserId(userId: String) {
       redisTemplate.opsForValue().getAndDelete(RedisConst.makeUserTokenKey(userId))
    }

}


