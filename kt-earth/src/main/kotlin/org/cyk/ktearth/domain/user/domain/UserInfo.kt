package org.cyk.ktearth.domain.user.domain

import java.time.LocalDateTime

data class UserInfo (
    val id: String,
    val username: String,
    val password: String,
    val phone: String?, //手机号(唯一)
    val avatar: String, //头像
    val auth: UserAuth, //用户权限
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)
