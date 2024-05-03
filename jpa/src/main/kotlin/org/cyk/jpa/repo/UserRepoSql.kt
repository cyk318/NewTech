package org.cyk.jpa.repo

import org.cyk.jpa.model.Userinfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepoSql: JpaRepository<Userinfo, Long> {

    @Query(value = "select * from user_info where username = ?1 and age = ?2", nativeQuery = true)
    fun findByCond(username: String, age: Int): List<Userinfo>

}