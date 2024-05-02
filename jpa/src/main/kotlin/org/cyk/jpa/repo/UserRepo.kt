package org.cyk.jpa.repo

import org.cyk.jpa.model.Userinfo

interface UserRepo {

    fun save(obj: Userinfo): Userinfo
    fun del(id: Long)
    fun findAll(): List<Userinfo>
    fun findUserinfoById(id: Long): Userinfo?

}