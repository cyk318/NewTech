package component

import com.linecorp.armeria.client.ClientRequestContext
import com.linecorp.armeria.client.HttpClient
import com.linecorp.armeria.client.SimpleDecoratingClient
import com.linecorp.armeria.common.HttpRequest
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.server.DecoratingHttpServiceFunction
import com.linecorp.armeria.server.HttpService
import com.linecorp.armeria.server.ServiceRequestContext
import org.slf4j.LoggerFactory

/**
 * 自定义装饰器
 * @author yikang.chen
 */
class CustomDecorator(
    delegate: HttpClient
): SimpleDecoratingClient<HttpRequest, HttpResponse>(delegate) {

    companion object {
        private val log = LoggerFactory.getLogger(CustomDecorator::class.java)
        /**
         * 这里为了迎合 Armeria 的 Java API，只能先这样处理
         */
    }

    override fun execute(ctx: ClientRequestContext, req: HttpRequest): HttpResponse {
        log.error("请求目标端点: ${ctx.endpoint()}")

        return HttpResponse.of(0)
    }


}



class Custom2 : DecoratingHttpServiceFunction {

    override fun serve(delegate: HttpService, ctx: ServiceRequestContext, req: HttpRequest): HttpResponse {
        println("另一种装饰器 ...")
        return delegate.serve(ctx, req)
    }

}