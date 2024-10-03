package org.cyk.ktearth.infra.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import java.io.Serializable
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

data class IPGeoLocationInfo(
    val ip: String = "",
    val pro: String = "",
    val proCode: String = "",
    val city: String = "",
    val cityCode: String = "",
    val region: String = "",
    val regionCode: String = "",
    val addr: String = "",
    val regionNames: String = "",
    val err: String = ""
): Serializable

object IPUtils {

    private val httpClient = HttpClient.newHttpClient()
    private val mapper = ObjectMapper()

    /**
     * 获取客户端IP地址
     * 由于客户端的IP地址可能通过多个代理层转发，因此需要检查多个HTTP头字段以获取真实IP。
     * 此方法首先检查“x-forwarded-for”头，这是最常用的代理头，然后尝试其他不那么常见的头字段。
     * 如果所有尝试都失败，则回退到使用请求的远程地址。
     *
     * @param request HttpServletRequest对象，用于获取客户端IP地址。
     * @return 客户端的IP地址字符串。如果无法确定客户端IP，则返回请求的远程地址。
     */
    fun getClientIp(request: HttpServletRequest): String {
        val headers = listOf(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
            "X-Real-IP"
        )

        return headers
            .mapNotNull { request.getHeader(it) }
            .firstOrNull { it.isNotBlank() && !it.equals("unknown", ignoreCase = true) }
            ?: request.remoteAddr
    }

    fun getIPGeoLocationInfo(request: HttpServletRequest): IPGeoLocationInfo? {
        try {
            val ip =  getClientIp(request)
            val req = HttpRequest.newBuilder().GET()
                .uri(URI("http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=$ip"))
                .build()
            val resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString())
            if (resp.statusCode() == 200) {
                return mapper.readValue<IPGeoLocationInfo>(resp.body())
            } else {
                log.error("ip相关信息请求失败！code: ${resp.statusCode()} body: ${resp.body()}")
            }
        } catch (e: Exception) {
            log.error("ip相关信息请求失败！")
            e.printStackTrace()
        }
        return null
    }

    private val log = LoggerFactory.getLogger(IPUtils::class.java)

}
