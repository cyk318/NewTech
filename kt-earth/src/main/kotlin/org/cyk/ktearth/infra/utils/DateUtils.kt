package org.cyk.ktearth.infra.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date


object DateUtils {

    fun convertToDate(time: LocalDateTime): Date {
        val instant = time.atZone(ZoneId.systemDefault()).toInstant()
        return Date.from(instant)
    }

}