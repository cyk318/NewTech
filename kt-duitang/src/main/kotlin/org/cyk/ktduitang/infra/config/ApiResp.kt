package org.cyk.ktduitang.infra.config

import java.io.Serializable

/**
 * 用来处理作为 api http 的返回接口
 *
 * @author cyk
 */
data class ApiResp<T> private constructor (
    var code: Int,
    var msg: String,
    var data: T? = null
) : Serializable {

    companion object {
        private const val serialVersionUID: Long = -7802429932100563214L

        /**
         * 约定：成功响应，那么一定需要有数据
         */
        fun <T> ok(data: T): ApiResp<T> {
            return ApiResp(
                ApiStatus.SUCCESS.code,
                ApiStatus.SUCCESS.msg,
                data
            )
        }

        /**
         * 约定: 错误响应，一定没有数据
         */
        fun no(code: ApiStatus): ApiResp<ApiStatus> {
            return ApiResp(
                code.code,
                code.msg
            )
        }
    }
}
