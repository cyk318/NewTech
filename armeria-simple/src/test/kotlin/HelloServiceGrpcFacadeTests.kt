import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.MoreExecutors
import com.linecorp.armeria.client.grpc.GrpcClients
import com.linecorp.armeria.client.logging.LoggingClient
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats
import com.linecorp.armeria.server.Server
import org.cyk.armeria.grpc.hello.Hello.HelloReq
import org.cyk.armeria.grpc.hello.Hello.HelloResp
import org.cyk.armeria.grpc.hello.HelloServiceGrpc.HelloServiceBlockingStub
import org.cyk.armeria.grpc.hello.HelloServiceGrpc.HelloServiceFutureStub
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HelloServiceGrpcFacadeTests {
    companion object {

        private lateinit var stub: HelloServiceBlockingStub
        private lateinit var server: Server
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            server = ArmeriaGrpcBean.newServer(9000)
            server.start()
            //这里启动不是异步的，所以不用 Thread.sleep 等待
            stub = GrpcClients.newClient(
                "http://127.0.0.1:9000/",
                HelloServiceBlockingStub::class.java,
            )
        }
    }

    @Test
    fun test1() {
        val req = HelloReq.newBuilder()
            .setName("cyk")
            .build()
        val resp = stub.hello(req)
        assertTrue { resp.msg.isNotBlank() }
    }

}

class HelloServiceGrpcFacadeTests2 {

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            val server = ArmeriaGrpcBean.newServer(9000)
            server.start()
        }
    }

    @Test
    fun test() {
        val client = GrpcClients.newClient(
            "gproto+http://127.0.0.1:9000/",
            HelloServiceBlockingStub::class.java
        )

        val req = HelloReq.newBuilder()
            .setName("cyk")
            .build()
        val resp = client.hello(req)

        val expect = "hello cyk ~"
        require(resp.msg == expect ) { "expect: $expect, actual: ${resp.msg} "}
    }

    @Test
    fun testFutures() {
        val client = GrpcClients.newClient(
            "gproto+http://127.0.0.1:9000/",
            HelloServiceFutureStub::class.java
        )

        val req = HelloReq.newBuilder()
            .setName("cyk")
            .build()
        val futureResp = client.hello(req)

        Futures.addCallback(futureResp, object : FutureCallback<HelloResp> {
            override fun onSuccess(result: HelloResp?) {
                assertNotNull(result)
                val expect = "hello cyk ~"
                require(result.msg == expect ) { "expect: $expect, actual: ${result.msg} "}
            }
            override fun onFailure(t: Throwable) {
                t.printStackTrace()
            }
        }, MoreExecutors.directExecutor())
        // Ps: MoreExecutors.directExecutor() 是 Guava 提供的特殊 Executor 实现，它不会为任务创建新的线程，也不会在线程池中执行任务。
        // 相反，它会直接在调用任务提交方法的当前线程中执行任务。

        // 等待异步完成(仅为演示，实际可能需要更多的非阻塞方式处理)
        futureResp.get()
    }

    @Test
    fun testDecorator() {
        val client = GrpcClients.builder("gproto+http://127.0.0.1:9000/")
            .serializationFormat(GrpcSerializationFormats.PROTO) //使用 protobuf 序列化
            .responseTimeoutMillis(10000) // 响应超时时间为 10 秒
            .decorator(LoggingClient.newDecorator()) // 添加日志装饰器
            .build(HelloServiceBlockingStub::class.java)

        val req = HelloReq.newBuilder()
            .setName("cyk")
            .build()
        val resp = client.hello(req)

        val expect = "hello cyk ~"
        require(resp.msg == expect ) { "expect: $expect, actual: ${resp.msg} "}
    }

}