package org.cyk.ktearth.domain.user.domain

import java.util.Date

/**
 * 用户头像审核
 * 这里先简单设计:
 * 1.用户上传新头像 -> 新增审核信息
 * 2.审核通过 -> 删除审核信息, 修改用户头像信息
 * 3.审核不通过 -> 删除审核信息
 */
data class UserAvatarAudit (
    val userId: String,
    val avatar: String,
    val cTime: Long = Date().time,
)
