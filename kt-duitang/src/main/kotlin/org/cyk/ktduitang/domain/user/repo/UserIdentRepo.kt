package org.cyk.ktduitang.domain.user.repo

import jakarta.persistence.Id
import jakarta.persistence.Table
import org.cyk.ktduitang.infra.template.DatabaseTemplate
import org.springframework.stereotype.Service
import java.util.Date

@Table(name = "user_ident")
data class UserIdentDo (
    @Id
    val id: Long? = null, // 用户 id
    val username: String, // 用户名
    val password: String, // 密码
    val state: Int, //状态: 0正常(默认) 1封号 2管理员
    val cTime: Date,
    val uTime: Date,
)

@Service
class UserIdentRepo(
    val databaseTemplate: DatabaseTemplate<UserIdentDo, Long>
) {
}