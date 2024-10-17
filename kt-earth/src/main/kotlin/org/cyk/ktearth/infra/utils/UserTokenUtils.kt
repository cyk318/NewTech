package org.cyk.ktearth.infra.utils

import jakarta.servlet.http.HttpServletRequest
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.model.constants.HttpHeaderConst
import org.cyk.ktearth.infra.model.constants.JwtConst

object UserTokenUtils {

    fun generateTokenByUserId(userId: String): String {
        return JwtUtils.createToken(mapOf(
            JwtConst.USER_ID to userId
        ))
    }

    fun getUserIdByRequest(request: HttpServletRequest): String {
        val token = request.getHeader(HttpHeaderConst.TOKEN)
        return JwtUtils.getTokenInfo(token).claims[JwtConst.USER_ID]?.asString()
            ?: throw AppException(ApiStatus.NOT_LOGIN, "用户没有登录")
    }

    fun getTokenByRequest(request: HttpServletRequest): String {
        return request.getHeader(HttpHeaderConst.TOKEN)
    }

}