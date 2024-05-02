package org.cyk.jpa.repo

import org.cyk.jpa.model.Userinfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserinfoMapper: JpaRepository<Userinfo, Long>