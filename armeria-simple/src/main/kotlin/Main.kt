import com.linecorp.armeria.common.logging.LoggingDecoratorBuilder
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.grpc.GrpcService
import component.Custom2
import component.CustomDecorator
import service.HelloServiceGrpcFacade

object ArmeriaGrpcBean {

    fun newServer(port: Int): Server {
        return Server.builder()
            .http(port)
            .service(
                GrpcService.builder()
                    .addService(HelloServiceGrpcFacade())
                    .build(),
//                listOf(CustomDecorator.newDecorator()) // 👈👈👈
            )
            .build()
    }

}

fun main(args: Array<String>) {
//    val log = LoggerFactory.getLogger("MainLogger")

    val server = ArmeriaGrpcBean.newServer(9000)
    server.closeOnJvmShutdown().thenRun {
//        log.info("Server is closed ...")
    }

    server.start().join()
}