package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.domain.user.repo.UserTokenRepo
import org.cyk.ktearth.infra.model.constants.RedisConst
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

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

}


