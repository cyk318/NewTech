package org.cyk.ktearth.infra.exception

import org.cyk.ktearth.infra.model.ApiStatus

class AppException (
    val status: ApiStatus,
    val log: String
) : RuntimeException(status.msg)

class FlowLimitException (
    val status: ApiStatus = ApiStatus.REQUEST_TOO_FAST,
    val log: String
) : RuntimeException(status.msg)