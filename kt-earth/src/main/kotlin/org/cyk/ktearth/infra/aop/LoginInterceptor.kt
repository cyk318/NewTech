package org.cyk.ktearth.infra.aop

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.domain.user.repo.UserTokenRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.utils.IPUtils
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

/**
 * 登录拦截器
 * 只有登录的用户才可以访问网页内容
 * @author yikang.chen
 */
@Component
class LoginInterceptor(
    private val userTokenRepo: UserTokenRepo,
    private val userInfoRepo: UserInfoRepo,
): HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val userId = UserTokenUtils.getUserIdByRequest(request)
        val user = userInfoRepo.queryById(userId)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "不存在的 userId: $userId")
        val oldToken = UserTokenUtils.getTokenByRequest(request)

        // 管理员特殊处理
        val requestURI = request.requestURI
        if (requestURI.startsWith("/admin")) {
            if (!user.isAdmin()) {
                throw AppException(ApiStatus.NO_PERMISSION, "没有权限 user: $user")
            }
        }

        // token 合法
        val userToken = userTokenRepo.queryByUserId(userId)
        if (userToken != null && !userToken.isExpire() && oldToken == userToken.token) {
            return true
        }
        throw AppException(ApiStatus.NOT_LOGIN, "用户未登录，已被拦截...  ip: ${IPUtils.getClientIp(request)}")
    }

}