package org.cyk.ktearth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.http.HttpServletRequest
import org.cyk.ktearth.domain.user.repo.UserAddrRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.Serializable
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.Executors

data class IPGeoInfo(
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

data class HttpServletRequestTiny (
    val headers: Map<String, String>,
    val remoteAddr: String,
)

data class SaveOrUpdateUserAddrCmd(
    val userId: String,
    val ip: String?,
    val addr: String?,
    val err: String,
)

@Service
class IPGeoInfoService(
    private val userAddrRepo: UserAddrRepo,
) {

    private val httpClient = HttpClient.newHttpClient()
    private val mapper = ObjectMapper()
    private val asyncUpdateIpGeoExecutor = Executors.newFixedThreadPool(2)

    /**
     * 异步更新 ip addr 信息
     * 通过太平洋网站获取具体的 ip addr 信息，速度较慢，这里采用异步方式
     */
    fun asyncUpdateIPGeoInfo(request: HttpServletRequest, userId: String) {
        //由于 request 只会存在主线程，其他线程无法获取上下文，因此使用 tiny 对象进行异步操作
        val ip = getIP(httpServletRequestTinyConvert(request))
        asyncUpdateIpGeoExecutor.submit {
            //请求获取用户 ip geo 信息
            val req = HttpRequest.newBuilder().GET()
                .uri(URI("http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=$ip"))
                .build()
            val resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString())

            if (resp.statusCode() != 200) {
                log.error("ip相关信息请求失败！code: ${resp.statusCode()} body: ${resp.body()}")
            } else {
                //数据同步到数据库
                val ipGeoInfo = mapper.readValue<IPGeoInfo>(resp.body())
                userAddrRepo.saveOrUpdateUserAddr (
                    SaveOrUpdateUserAddrCmd(
                        userId = userId,
                        ip = ipGeoInfo.ip,
                        addr = ipGeoInfo.addr,
                        err = ipGeoInfo.err,
                    )
                )
            }
        }
    }

    private fun getIP(request: HttpServletRequestTiny): String {
        val headers = listOf(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
            "X-Real-IP"
        )
        return headers
            .mapNotNull { request.headers[it] }
            .firstOrNull { it.isNotBlank() && !it.equals("unknown", ignoreCase = true) }
            ?: request.remoteAddr
    }

    /**
     * 在异步的情况下，主线程无法共享 request，因此这里先拿到所需要的参数
     */
    private fun httpServletRequestTinyConvert(request: HttpServletRequest): HttpServletRequestTiny {
        // 提取需要的 header
        val headers = mapOf(
            "X-Forwarded-For" to request.getHeader("X-Forwarded-For"),
            "Proxy-Client-IP" to request.getHeader("Proxy-Client-IP"),
            "WL-Proxy-Client-IP" to request.getHeader("WL-Proxy-Client-IP"),
            "HTTP_CLIENT_IP" to request.getHeader("HTTP_CLIENT_IP"),
            "HTTP_X_FORWARDED_FOR" to request.getHeader("HTTP_X_FORWARDED_FOR"),
            "X-Real-IP" to request.getHeader("X-Real-IP")
        )

        // 提取 remoteAddr
        val remoteAddr = request.remoteAddr

        return HttpServletRequestTiny(
            headers = headers,
            remoteAddr = remoteAddr,
        )
    }

    private val log = LoggerFactory.getLogger(IPGeoInfoService::class.java)

}
