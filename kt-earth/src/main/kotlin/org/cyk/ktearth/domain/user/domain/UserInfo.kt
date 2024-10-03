package org.cyk.ktearth.domain.user.domain


data class UserInfo (
    val id: String? = null,
    val username: String, //花名(不能重复)
    val password: String,
    val phone: String? = null, //手机号(唯一)
    val avatar: String = "default", //头像
    val auth: UserAuth = UserAuth.NORMAL, //用户权限
    val cTime: Long,
    val uTime: Long,
)