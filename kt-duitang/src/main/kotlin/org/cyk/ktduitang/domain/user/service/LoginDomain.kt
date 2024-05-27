package org.cyk.ktduitang.domain.user.service

import io.netty.util.Timeout
import org.cyk.ktduitang.domain.user.repo.UserinfoRepoImpl
import org.cyk.ktduitang.facade.LoginDto
import org.cyk.ktduitang.infra.config.ApiStatus
import org.cyk.ktduitang.infra.config.AppException
import org.cyk.ktduitang.infra.tools.JwtUtils
import org.cyk.ktduitang.infra.tools.PasswordUtils
import org.cyk.ktduitang.infra.tools.TokenUtils
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class LoginDomain(
    val userInfoRepoImpl: UserinfoRepoImpl,
    val redisTemplate: StringRedisTemplate,
) {

    fun ckValidAndGenerateToken(dto: LoginDto): String {
        val userinfo = userInfoRepoImpl.queryUserIdentByUsername(dto.username)
            ?: throw AppException(ApiStatus.INVALID_PARAM, "用户名不存在！username: ${dto.username}")
        if (!PasswordUtils.isOk(dto.password, userinfo.password)) {
            throw AppException(ApiStatus.INVALID_PARAM, "密码错误！username: ${dto.username}, password: ${dto.password}")
        }
        return JwtUtils.createToken(
            mapOf("id" to userinfo.id.toString())
        )
    }

    fun saveTokenToRedis(
        dto: LoginDto,
        token: String,
    ) {
        //1.自定义 token key
        val userId = TokenUtils.getUserIdByToken(token)
        val tokenKey = TokenUtils.generateTokenKey(userId)
        //2.保存到 Redis 上
        redisTemplate.opsForValue().set(tokenKey, token, 1, TimeUnit.DAYS)
    }

}

