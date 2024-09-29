import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.grpc.GrpcService
import service.BlogServiceGrpcFacade

object ArmeriaGrpcBean {
//    // 创建一个具有固定线程数的线程池
//    ThreadPoolExecutor blockingTaskExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
//
//    // 使用 ServerBuilder 自定义阻塞任务线程池
//    Server server = new ServerBuilder()
//    .service(GrpcService.builder()
//    .addService(new BlogService())
//    .exceptionMapping(new GrpcExceptionHandler())
//    .useBlockingTaskExecutor(true) // 启用阻塞任务执行器
//    .build())
//    .blockingTaskExecutor(blockingTaskExecutor, true) // 配置自定义线程池
//    .http(8080)
//    .build();
//
//    server.start().join();

    fun newServer(port: Int): Server {
        return Server.builder()
            .http(port) // 1.配置端口号
            .service(
                GrpcService.builder()
                    .addService(BlogServiceGrpcFacade()) // 2.添加服务示例
                    .build()
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