package org.cyk.ktearth.infra.repo.user

import org.cyk.ktearth.service.SaveOrUpdateUserAddrInfoCmd
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import java.util.*

@Document("user_addr")
data class UserAddr (
    @Id
    val userId: String,
    val ip: String?,
    val addr: String?,
    val err: String,
    val uTime: Long,
)

//@Repository
//class UserAddrRepoImpl(
//    private val mongoTemplate: MongoTemplate,
//): UserIPGeoInfoRepo {
//
//    override fun saveOrUpdateUserAddrInfo(cmd: SaveOrUpdateUserAddrInfoCmd) {
//        val exists = existsUserAddrInfoByUserId(cmd.userId)
//        if (exists) {
//            val q = Query.query(Criteria.where("_id").`is`(cmd.userId))
//            val u = Update()
//                .set("ip", cmd.ip)
//                .set("addr", cmd.addr)
//                .set("u_time", Date().time)
//            mongoTemplate.updateFirst(q, u, UserAddr::class.java)
//        } else {
//            val obj = map(cmd)
//            mongoTemplate.save(obj)
//        }
//    }
//
//    override fun existsUserAddrInfoByUserId(userId: String): Boolean {
//        val q = Query.query(Criteria.where("_id").`is`(userId))
//        return mongoTemplate.exists(q, UserAddr::class.java)
//    }
//
//    private fun map(cmd: SaveOrUpdateUserAddrInfoCmd): UserAddr = with(cmd) {
//        UserAddr(
//            userId = userId,
//            ip = ip,
//            addr = addr,
//            err = err,
//            uTime = Date().time,
//        )
//    }
//
//}