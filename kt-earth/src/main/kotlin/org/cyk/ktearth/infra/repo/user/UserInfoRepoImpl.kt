package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.domain.user.domain.UserAuth
import org.cyk.ktearth.infra.utils.DateUtils
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Date

@Document("user_info")
data class UserInfoDo (
    @Id
    val id: String? = null,
    val username: String,
    val password: String,
    val phone: String? = null, //手机号(唯一)
    val avatar: String = "default", //头像
    val auth: UserAuth = UserAuth.NORMAL, //用户权限
    val cTime: Long = Date().time,
    val uTime: Long = Date().time,
)

//@Repository
//class UserInfoRepoImpl(
//    private val mongoTemplate: MongoTemplate
//): UserInfoRepo {
//
//    override fun save(cmd: UserSaveRegInfoCmd) {
//        val o = map(cmd)
//        mongoTemplate.save(o)
//    }
//
//    override fun queryByUsername(username: String): UserInfo? {
//        val q = Query.query(Criteria.where("username").`is`(username))
//        return mongoTemplate.findOne(q, UserInfoDo::class.java)?.let { map(it) }
//    }
//
//    override fun queryById(id: String?): UserInfo? {
//        if (id == null) return null
//        val q = Query.query(Criteria.where("_id").`is`(id))
//        return mongoTemplate.findOne(q, UserInfoDo::class.java)?.let { map(it) }
//    }
//
//    override fun queryByIds(ids: List<String>): List<UserInfo> {
//        if (ids.isEmpty()) {
//            return emptyList()
//        }
//        val q = Query.query(Criteria.where("_id").`in`(ids))
//        return mongoTemplate.find(q, UserInfoDo::class.java).map { map(it) }
//    }
//
//    private fun map(o: UserInfoDo) = with(o) {
//        UserInfo(
//            id = id!!,
//            username = username,
//            password = password,
//            phone = phone,
//            avatar = avatar,
//            auth = auth,
//            cTime = cTime,
//            uTime = uTime,
//        )
//    }
//
//    private fun map(cmd: UserSaveRegInfoCmd) = with(cmd) {
//        UserInfoDo(
//            username = username,
//            password = saltPassword,
//        )
//    }
//
//}
//
//
