package org.cyk.jpa.repo

import org.cyk.jpa.model.Userinfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepoJpa: JpaRepository<Userinfo, Long>
