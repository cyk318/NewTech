package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.springframework.stereotype.Component

data class UserLoginCmd (
    val username: String,
    val password: String,
)

@Component
class UserLoginHandler(
    private val userInfoRepo: UserInfoRepo
): ApplicationHandler<UserLoginCmd, String> {

    override fun handler(input: UserLoginCmd): String {
        TODO()
    }

}