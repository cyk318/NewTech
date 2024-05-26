package org.cyk.ktduitang.infra.template

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.springframework.stereotype.Repository

@Repository
interface DatabaseTemplate<T>: BaseMapper<T>