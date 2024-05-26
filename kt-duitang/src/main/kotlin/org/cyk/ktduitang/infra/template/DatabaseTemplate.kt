package org.cyk.ktduitang.infra.template

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DatabaseTemplate<T, ID>: JpaRepository<T, ID>, Specification<T>