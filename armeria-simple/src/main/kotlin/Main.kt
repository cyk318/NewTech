
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.grpc.GrpcService
import component.CustomDecorator
import service.HelloServiceGrpcFacade


object ArmeriaGrpcBean {

    fun newServer(port: Int): Server {
        return Server.builder()
            .http(port)
            .service(
                GrpcService.builder()
                    .addService(HelloServiceGrpcFacade())
                    .enableUnframedRequests(true) // 👈👈👈 启用无框请求
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