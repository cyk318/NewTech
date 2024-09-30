package component

import com.linecorp.armeria.common.HttpRequest
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.server.*
import org.slf4j.LoggerFactory
import java.util.function.Function

/**
 * 自定义装饰器
 * @author yikang.chen
 */
class CustomDecorator(
    delegate: HttpService,
) : DecoratingHttpServiceFunction, SimpleDecoratingHttpService(delegate) {

    companion object {
        private val log = LoggerFactory.getLogger(CustomDecorator::class.java)

        /**
         * 这里为了迎合 Armeria 的 Java API，只能先这样处理
         */
        fun newDecorator(): Function<in HttpService, out CustomDecorator> {
            return Function { delegate ->
                CustomDecorator(delegate)
            }
        }
    }

    override fun serve(ctx: ServiceRequestContext, req: HttpRequest): HttpResponse {
        log.info("======================================================")
        log.info("收到客户端 rpc header: ${req.headers()}")
        req.aggregate().thenApply { req ->
            log.info("收到客户端 rpc body: ${req.contentUtf8()}")
        }
        log.info("======================================================")

        return unwrap().serve(ctx, req)
    }

}

class Custom2 : DecoratingHttpServiceFunction {

    override fun serve(delegate: HttpService, ctx: ServiceRequestContext, req: HttpRequest): HttpResponse {
        println("另一种装饰器 ...")
        return delegate.serve(ctx, req)
    }

}