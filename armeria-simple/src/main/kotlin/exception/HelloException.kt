package exception

import com.linecorp.armeria.common.RequestContext
import com.linecorp.armeria.common.grpc.GrpcExceptionHandlerFunction
import io.grpc.Metadata
import io.grpc.Status

/**
 * 自定义异常
 * @author: yikang.chen
 */
class HelloException (
    errorMsg: String
): IllegalStateException(errorMsg)

/**
 * 统一异常处理
 * @author: yikang.chen
 */
class GrpcExceptionHandler: GrpcExceptionHandlerFunction {
    override fun apply(ctx: RequestContext, status: Status, cause: Throwable, metadata: Metadata): Status? {
        when (cause) {
            is HelloException -> Status.NOT_FOUND.withCause(cause).withDescription(cause.message)
            is IllegalArgumentException -> return Status.INVALID_ARGUMENT.withCause(cause)
            else -> return null
        }
        return null
    }

}