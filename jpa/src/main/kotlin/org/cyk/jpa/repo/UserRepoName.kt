package org.cyk.jpa.repo

import org.cyk.jpa.model.Userinfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepoName: JpaRepository<Userinfo, Long> {

    //直接查询
    fun findByUsername(username: String): List<Userinfo>

    //模糊查询
    fun findByUsernameLike(username: String): List<Userinfo>

    //and 查询
    fun findByUsernameAndAge(username: String, age: Int): List<Userinfo>

    //小于等于查询
    fun findByIdLessThanEqual(id: Long): List<Userinfo>

    //between 查询
    fun findByIdBetween(start: Long, end: Long): List<Userinfo>

    //in 查询
    fun findByIdIn(ids: List<Long>): List<Userinfo>

}