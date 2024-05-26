package org.cyk.ktduitang.infra.config

class AppException(
    val status: ApiStatus,
    val log: String
) : RuntimeException(status.msg)
