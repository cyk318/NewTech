package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.domain.UserInfo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component
import java.util.*

data class QueryUserInfoCmd(
    val id: String,
)

@Component
class QueryUserInfoHandler(
    private val userInfoRepo: UserInfoRepo,
): ApplicationHandler<QueryUserInfoCmd, UserInfoVo> {

    override fun handler(input: QueryUserInfoCmd): UserInfoVo {
        if (input.id.isBlank()) {
            throw AppException(ApiStatus.INVALID_REQUEST, "用户 id 不能为空串")
        }
        val userinfo = userInfoRepo.queryById(input.id)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "用户不存在")
        return buildVo(userinfo)
    }

    private fun buildVo(userinfo: UserInfo): UserInfoVo = with(userinfo) {
        UserInfoVo(
            id = id!!,
            username = username,
            phone = phone,
            avatar = avatar,
            cTime = cTime.time,
            uTime = uTime.time,
        )
    }

}

data class UserInfoVo(
    var id: String,
    val username: String, //花名(不能重复)
    val phone: String?, //手机号(唯一)
    var avatar: String, //头像
    val cTime: Long,
    val uTime: Long,
)
