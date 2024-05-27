package org.cyk.ktduitang.domain.user.design.cmd

import org.cyk.ktduitang.domain.user.repo.UserIdentRepo
import org.springframework.stereotype.Component

@Component
class SaveUserinfo(
    private val userIdentRepo: UserIdentRepo
) {

    fun handler() {

    }

}