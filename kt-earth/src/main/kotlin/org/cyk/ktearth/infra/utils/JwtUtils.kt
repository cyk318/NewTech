package org.cyk.ktearth.infra.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.model.JwtConst
import java.util.*


 
/**
 * Jwt 工具类
 */
object JwtUtils {
 
    //自定义密钥
    private const val SIGN = "Y*(GY*G^&*Hhp*)(8kljH@uia%69g*()&"

    /**
     * 生成 Token
     * @param map 自定义的载荷数据
     * @return 返回 Token
     */
    fun createToken(map: Map<String, String>): String {
        //1.设置过期时间
        val expireTime = Calendar.getInstance().apply {
                this.add(Calendar.DATE, JwtConst.EXPIRE_TIME)
            }.time
 
        //2.创建 jwt builder，添加自定义的载荷数据
        val builder = JWT.create()
        for ((key, value) in map) {
            builder.withClaim(key, value)
        }
 
        //3.生成 Token
        return builder.withExpiresAt(expireTime) //过期时间
            .sign(Algorithm.HMAC256(SIGN)) // sign
    }
 
    /**
     * 验证 Token 合法性
     * @param token
     */
    fun checkToken(token: String): Boolean {
        return try {
            JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
 
    /**
     * 获取 Token 信息
     * @param token
     * @return
     */
    fun getTokenInfo(token: String): DecodedJWT {
        try {
            return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token)
        } catch (e: TokenExpiredException) {
            throw AppException(ApiStatus.JWT_EXPIRE, "jwt 过期！")
        }
    }
}