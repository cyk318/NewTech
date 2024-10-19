package org.cyk.ktearth.domain.user.domain

import java.util.Date


data class UserInfo (
    var id: String? = null,
    val username: String, //花名(不能重复)
    val password: String,
    val phone: String? = null, //手机号(唯一)
    var avatar: String? = null, //头像
    val auth: UserAuth = UserAuth.NORMAL, //用户权限
    val cTime: Date = Date(),
    val uTime: Date = Date(),
) {

    fun isAdmin(): Boolean {
        return this.auth == UserAuth.ADMIN
    }

}


enum class UserAuth (
    val code: Int,
    val desc: String,
) {
    NORMAL(1, "普通用户"),
    ADMIN(2, "管理员"),
    BANNED(3, "封禁"),
    ;

    companion object {
        fun codeOf(code: Int) = entries.firstOrNull { it.code == code }
    }

}
