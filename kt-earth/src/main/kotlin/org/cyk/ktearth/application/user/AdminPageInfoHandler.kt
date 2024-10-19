package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.domain.UserInfo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.model.PageResp
import org.springframework.stereotype.Component

data class AdminPageInfoCmd(
    val start: Int,
    val limit: Int,
    val id: String?,
    val username: String?,
    val auth: Int?,
)

data class AdminPageInfoVo(
    var id: String,
    val username: String, //花名(不能重复)
    val phone: String?, //手机号(唯一)
    var avatar: String?, //头像
    val auth: String, //用户权限
    val cTime: Long,
    val uTime: Long,
)

@Component
class AdminPageInfoHandler(
    private val userInfoRepo: UserInfoRepo,
): ApplicationHandler<AdminPageInfoCmd, PageResp<AdminPageInfoVo>> {

    override fun handler(input: AdminPageInfoCmd): PageResp<AdminPageInfoVo> {
        val pageRes = userInfoRepo.page(input)
        return PageResp.ok(
            more = pageRes.hasMore,
            nextStart = pageRes.nextStart,
            objectList = pageRes.result.map { map(it) },
            total = pageRes.total
        )
    }

    private fun map(it: UserInfo): AdminPageInfoVo = with(it) {
        AdminPageInfoVo(
            id = id!!,
            username = username,
            phone = phone,
            avatar = avatar,
            auth = auth.desc,
            cTime = cTime.time,
            uTime = uTime.time,
        )
    }

}

