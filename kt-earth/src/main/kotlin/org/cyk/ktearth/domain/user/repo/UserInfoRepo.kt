package org.cyk.ktearth.domain.user.repo

import org.cyk.ktearth.application.user.AdminPageInfoCmd
import org.cyk.ktearth.domain.user.domain.UserInfo
import org.cyk.ktearth.infra.model.PageResp

interface UserInfoRepo {

    fun save(obj: UserInfo): UserInfo

    fun update(obj: UserInfo)

    fun queryByUsername(username: String): UserInfo?

    fun queryById(id: String?): UserInfo?

    fun queryByIds(ids: List<String>): List<UserInfo>
    fun page(cmd: AdminPageInfoCmd): PageResp<UserInfo>

}