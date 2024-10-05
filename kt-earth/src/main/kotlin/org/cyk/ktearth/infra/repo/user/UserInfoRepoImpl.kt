package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.domain.user.domain.UserAuth
import org.cyk.ktearth.domain.user.domain.UserInfo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.update
import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.meta.When

@Document("user_info")
data class UserInfoDo (
    @Id
    val id: String?,
    val username: String, //花名(不能重复)
    val password: String,
    val phone: String?, //手机号(唯一)
    val avatar: String?, //头像
    val auth: UserAuth, //用户权限
    val cTime: Long,
    val uTime: Long,
)

@Repository
class UserInfoRepoImpl(
    private val mongoTemplate: MongoTemplate
): UserInfoRepo {

    override fun save(obj: UserInfo): UserInfo {
        val o = map(obj)
        mongoTemplate.save(o).let { obj.id = it.id }
        return obj
    }

    override fun update(obj: UserInfo) {
        val q = Query.query(Criteria.where("_id").`is`(obj.id))
        val u = Update().apply {
            set("username", obj.username)
            set("password", obj.password)
            obj.phone?.let { set("phone", it) }
            obj.avatar?.let { set("avatar", it) }
            set("auth", obj.auth)
            set("u_time", Date().time)
        }
        mongoTemplate.updateFirst(q, u, UserInfoDo::class.java)
    }

    override fun queryByUsername(username: String): UserInfo? {
        val q = Query.query(Criteria.where("username").`is`(username))
        return mongoTemplate.findOne(q, UserInfoDo::class.java)?.let { map(it) }
    }

    override fun queryById(id: String?): UserInfo? {
        if (id == null) return null
        val q = Query.query(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(q, UserInfoDo::class.java)?.let { map(it) }
    }

    override fun queryByIds(ids: List<String>): List<UserInfo> {
        if (ids.isEmpty()) {
            return emptyList()
        }
        val q = Query.query(Criteria.where("_id").`in`(ids))
        return mongoTemplate.find(q, UserInfoDo::class.java).map { map(it) }
    }

    private fun map(o: UserInfoDo) = with(o) {
        UserInfo(
            id = id,
            username = username,
            password = password,
            phone = phone,
            avatar = avatar,
            auth = auth,
            cTime = cTime,
            uTime = uTime,
        )
    }

    private fun map(obj: UserInfo) = with(obj) {
        UserInfoDo(
            id = id,
            username = username,
            password = password,
            phone = phone,
            avatar = avatar,
            auth = auth,
            cTime = cTime,
            uTime = uTime,
        )
    }

}
