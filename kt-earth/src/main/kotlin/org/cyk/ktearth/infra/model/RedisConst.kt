package org.cyk.ktearth.infra.model

object RedisConst {

    private const val JWT_PREFIX = "jwt:"

    fun makeJwtKey(userId: String) = "$JWT_PREFIX$userId"

}