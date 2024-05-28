package org.cyk.ktduitang.domain.user.design.cmd

import org.cyk.ktduitang.domain.user.repo.UserDetailRepo
import org.cyk.ktduitang.domain.user.repo.UserIdentRepo
import org.cyk.ktduitang.facade.model.RegDto
import org.springframework.stereotype.Component

@Component
class SaveUserinfoHandler(
    private val userIdentRepo: UserIdentRepo,
    private val userDetailRepo: UserDetailRepo,
) {

    fun handler(dto: RegDto) {
        userIdentRepo.save(dto)
        userDetailRepo.save(dto.id!!)
    }

}