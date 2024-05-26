package org.cyk.ktduitang.infra.config
import java.io.Serializable

/**
 * 类说明: 分页请求
 *
 * @author cyk
 */
data class PageResp<T> (
    var result: List<T>? = null, //结果
    //是否有更多的数据: 判断请求中的limit 是否等于查询到的长度即可
    //例如 offset = 0, limit = 3. 而查询的时候按照 limit + 1 来查询，如果查询的结果长度 size == limit + 1 就说明还有数据
    var hasMore: Boolean? = null,
    var nextStart: Long? = null,
    var total: Long? = null
) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 260564179048006430

        fun <T> ok(objectList: List<T>?, total: Long?): PageResp<T> {
            return PageResp(result = objectList, total = total)
        }

        fun <T> ok(more: Boolean?, nextStart: Long?, objectList: List<T>?): PageResp<T> {
            return PageResp(hasMore = more, nextStart = nextStart, result = objectList)
        }

        fun <T> ok(more: Boolean?, nextStart: Long?, objectList: List<T>?, total: Long?): PageResp<T> {
            return PageResp(hasMore = more, nextStart = nextStart, result = objectList, total = total)
        }

        fun empty(): PageResp<Any> {
            return PageResp()
        }
    }

    fun isEmpty(): Boolean {
        return (result == null || result!!.isEmpty()) && (hasMore == null)
    }
}
