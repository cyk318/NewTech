package org.cyk.jpa.repo

import org.cyk.jpa.model.Userinfo
import org.springframework.stereotype.Service

@Service
class UserRepoImpl(
    private val userinfoMapper: UserinfoMapper
): UserRepo {

    override fun save(obj: Userinfo): Userinfo {
        return userinfoMapper.save(obj)
    }

    override fun del(id: Long) {
        userinfoMapper.deleteById(id)
    }

    override fun findAll(): List<Userinfo> {
        return userinfoMapper.findAll()
    }

    override fun findUserinfoById(id: Long): Userinfo? {
        return userinfoMapper.findById(id).orElse(null)
    }

}