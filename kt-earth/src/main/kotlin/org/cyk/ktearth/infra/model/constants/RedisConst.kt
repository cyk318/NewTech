package org.cyk.ktearth.infra.model.constants


object RedisConst {

    /**
     * @see makeUserTokenKey
     */
    private const val USER_TOKEN_PREFIX = "user_token:"

    fun makeUserTokenKey(userId: String) = USER_TOKEN_PREFIX + userId

}