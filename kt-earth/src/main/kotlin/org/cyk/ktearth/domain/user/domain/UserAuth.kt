package org.cyk.ktearth.domain.user.domain

enum class UserAuth (
    val code: Int,
    val desc: String,
) {
    NORMAL(1, "普通用户"),
    ADMIN(2, "管理员"),
    BANNED(3, "封禁"),
    ;

    companion object {
        fun of(code: Int) = entries.firstOrNull { it.code == code }
    }

}
