package org.cyk.jpa.repo

import org.cyk.jpa.model.Userinfo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepoJpql: JpaRepository<Userinfo, Long> {

    //占位符从 1 开始
    @Query("from Userinfo where username = ?1 and age <= ?2")
    fun findByCond1(username: String, age: Int): List<Userinfo>

    //参数名 ":" 绑定
    @Query("from Userinfo where username = :username and age <= :age")
    fun findByCond2(@Param("username") username: String,
                    @Param("age") age: Int): List<Userinfo>

    //模糊查询 + 排序
    @Query("from Userinfo where username like %:username% order by cTime desc")
    fun findByCond3(@Param("username") username: String): List<Userinfo>

    //模糊查询 + 分页
    @Query("from Userinfo where username like %:username%")
    fun findByCond4(pageable: Pageable, @Param("username") username: String): List<Userinfo>

    //in 查询
    @Query("from Userinfo where age in :ages")
    fun findByCond5(@Param("ages") ages: List<Int>): List<Userinfo>

    //通过对象进行查询(SPEL 表达式查询)
    @Query("from Userinfo where username = :#{#userinfo.username} and age <= :#{#userinfo.age}")
    fun findByCond6(@Param("userinfo") userinfo: Userinfo): List<Userinfo>

}

