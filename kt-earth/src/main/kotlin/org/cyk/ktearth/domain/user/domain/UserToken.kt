package org.cyk.ktearth.domain.user.domain

import org.cyk.ktearth.infra.utils.DateUtils
import org.cyk.ktearth.infra.utils.UserTokenUtils
import java.time.LocalDateTime
import java.util.Date

data class UserToken (
    var userId: String,
    var token: String,
    var expireDate: Date,
    val cTime: Date = Date(),
    var uTime: Date = Date(),
) {

    /**
     * 重置 token 信息:
     * 1.更新 token
     * 2.更新过期时间: 当前时间 + 30天
     */
    fun resetToken() {
        this.token = UserTokenUtils.generateTokenByUserId(this.userId)
        this.expireDate = DateUtils.convertToDate(LocalDateTime.now().plusDays(30))
        this.uTime = Date()
    }

    fun isExpire(): Boolean {
        return this.expireDate.before(Date())
    }

    /**
     * 为退出登录提供
     * 设置 token 过期时间为当前时间
     */
    fun makeExpireNow() {
        val now = Date()
        this.expireDate = now
        this.uTime = now
    }

}


