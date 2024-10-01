
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.grpc.GrpcService
import component.CustomDecorator
import exception.GrpcExceptionHandler
import service.HelloServiceGrpcFacade


object ArmeriaGrpcBean {

    fun newServer(port: Int): Server {
        // 启用详细异常响应 👈👈👈
//        System.setProperty("com.linecorp.armeria.verboseResponses", "true");

        return Server.builder()
            .http(port)
            .service(
                GrpcService.builder()
                    .addService(HelloServiceGrpcFacade())
                    .enableUnframedRequests(true)
                    .exceptionHandler(GrpcExceptionHandler())
                    .build(),
                CustomDecorator.newDecorator(),
            )
            .build()
    }

}

fun main() {
    val server = ArmeriaGrpcBean.newServer(9000)
    server.start().join()
}