package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.infra.utils.JwtUtils
import org.cyk.ktearth.infra.model.JwtConst
import org.cyk.ktearth.infra.model.RedisConst
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

//@Repository
//class UserJwtRepoImpl(
//    private val redisTemplate: StringRedisTemplate
//): UserJwtRepo {
//
//    override fun generateAndSave(userId: String): String {
//        //1.创建 Jwt
//        val jwt = JwtUtils.createToken(mapOf(JwtConst.USER_ID to userId))
//        //2.缓存
//        redisTemplate.opsForValue().set(RedisConst.makeJwtKey(userId), jwt, JwtConst.EXPIRE_TIME.toLong(), TimeUnit.DAYS)
//        return jwt
//    }
//
//    override fun getJwtByUserId(userId: String): String? {
//        return redisTemplate.opsForValue().get(RedisConst.makeJwtKey(userId))
//    }
//
//}