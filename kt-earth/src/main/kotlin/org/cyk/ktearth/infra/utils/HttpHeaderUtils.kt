package org.cyk.ktearth.infra.utils

import jakarta.servlet.http.HttpServletRequest
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.model.JwtConst

object HttpHeaderConst {
    const val JWT_KEY = "pq_auth"
}

object HttpHeaderUtils {

    /**
     * 用户必须登录
     */
    fun getUserIdByJwtForHard(r: HttpServletRequest): String {
        val jwt = r.getHeader(HttpHeaderConst.JWT_KEY)
            ?: throw AppException(ApiStatus.TOKEN_EXPIRE, "token 过期！")
        val userId = JwtUtils.getTokenInfo(jwt).getClaim(JwtConst.USER_ID)?.asString()
            ?: throw AppException(ApiStatus.TOKEN_EXPIRE, "token 过期！")
        val ok = JwtUtils.checkToken(jwt)
        if (!ok) {
            throw AppException(ApiStatus.TOKEN_EXPIRE, "token 过期！ userId: $userId")
        }
        return userId
    }

    /**
     * 用户不必须登录
     */
    fun getUserIdByJwtForSoft(r: HttpServletRequest): String? {
        try {
            val jwt = r.getHeader(HttpHeaderConst.JWT_KEY)
                ?: return null
            val userId = JwtUtils.getTokenInfo(jwt).getClaim(JwtConst.USER_ID)?.asString()
                ?: return null
            val ok = JwtUtils.checkToken(jwt)
            if (!ok) {
                return null
            }
            return userId
        } catch (e: Exception) {
            return null
        }
    }

}