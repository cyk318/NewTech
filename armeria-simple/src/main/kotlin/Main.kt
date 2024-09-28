import org.slf4j.LoggerFactory
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.grpc.GrpcService
import service.BlogService

object ArmeriaGrpcBean {
    fun newServer(port: Int): Server {
        return Server.builder()
            .http(port) // 1.配置端口号
            .service(
                GrpcService.builder()
                    .addService(BlogService()) // 2.添加服务示例
                    .build()
            )
            .build()
    }
}

fun main(args: Array<String>) {
    val log = LoggerFactory.getLogger("MainLogger")

    val server = ArmeriaGrpcBean.newServer(9000)
    server.closeOnJvmShutdown().thenRun {
        log.info("Server is closed ...")
    }

    server.start().join()
}