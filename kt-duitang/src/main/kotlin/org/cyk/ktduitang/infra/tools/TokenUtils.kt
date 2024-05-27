package org.cyk.ktduitang.infra.tools

import org.slf4j.LoggerFactory
import java.lang.Exception

object TokenUtils {

    fun generateTokenKey(userId: Long): String = "TOKEN_$userId"
    fun getUserIdByToken(token: String): Long {
        var id = -1L
        try {
            val c = JwtUtils.getTokenInfo(token).getClaim("id")
            id = c.asString().toLong()
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("从 token 中获取用户 id 异常")
        }
        return id
    }

    private val log = LoggerFactory.getLogger(TokenUtils::class.java)

}