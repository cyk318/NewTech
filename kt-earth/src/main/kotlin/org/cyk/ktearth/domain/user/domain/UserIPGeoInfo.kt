package org.cyk.ktearth.domain.user.domain

import java.time.LocalDateTime

data class UserIPGeoInfo(
    val username: String,
    val ip: String?,
    val pro: String?,
    val proCode: String?,
    val city: String?,
    val cityCode: String?,
    val region: String?,
    val regionCode: String?,
    val addr: String?,
    val regionNames: String?,
    val err: String?,
    val uTime: LocalDateTime,
)
