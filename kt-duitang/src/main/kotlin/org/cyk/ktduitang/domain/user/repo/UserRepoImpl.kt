package org.cyk.ktduitang.domain.user.repo

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import org.cyk.ktduitang.domain.user.model.UserIdent
import org.cyk.ktduitang.domain.user.model.UserIdentDo
import org.cyk.ktduitang.infra.template.DatabaseTemplate
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepoImpl (
    val databaseTemplate: DatabaseTemplate<UserIdentDo>,
    val mongoTemplate: MongoTemplate,
): UserRepo {

    override fun queryUserIdentByUsername(username: String): UserIdent? {
        val q = LambdaQueryWrapper<UserIdentDo>()
        q.eq(UserIdentDo::username, username)
        return databaseTemplate.selectOne(q)
            ?.let(::map)
    }

    private fun map(o: UserIdentDo): UserIdent = with(o) {
        UserIdent(
            id = id!!,
            username = username,
            password = password,
            state = state,
            cTime = cTime,
            uTime = uTime,
        )
    }

}
