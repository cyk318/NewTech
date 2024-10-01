
import com.alibaba.nacos.api.NacosFactory
import com.alibaba.nacos.api.naming.pojo.Instance
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.grpc.GrpcService
import exception.GrpcExceptionHandler
import service.HelloServiceGrpcFacade
import java.util.*


object ArmeriaGrpcBean {

    fun newServer(port: Int): Server {
        return Server.builder()
            .http(port)
            .service(
                GrpcService.builder()
                    .addService(HelloServiceGrpcFacade())
                    .enableUnframedRequests(true)
                    .exceptionHandler(GrpcExceptionHandler())
                    .build(),
            )
            .serviceUnder("/docs", DocService()) // 👈👈👈 添加文档服务
            .build()
    }

}

fun main() {

    val server1 = ArmeriaGrpcBean.newServer(9001)
    val server2 = ArmeriaGrpcBean.newServer(9002)
    server1.start().join()
    server2.start().join()

    // 连接 nacos，并注册集群
    val nacos = NacosFactory.createNamingService(
        Properties().apply {
            put("serverAddr", "100.64.0.0:8848")
            put("namespace", "0dc9a7f0-5f97-445a-87e5-9fe6869d6708") //可选，默认 public (命名空间需要提前在 nacos 客户端上创建，此处填写命名空间ID)
        }
    )
    val instance1 = Instance().apply {
        ip = "100.94.135.96"
        port = 9001
        clusterName = "grpc-hello"
    }
    val instance2 = Instance().apply {
        ip = "100.94.135.96"
        port = 9002
        clusterName = "grpc-hello"
    }
    nacos.batchRegisterInstance("helloGrpcService", "DEFAULT", listOf(instance1, instance2))
}

