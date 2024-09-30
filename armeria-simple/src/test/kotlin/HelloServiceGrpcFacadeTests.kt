import com.linecorp.armeria.client.grpc.GrpcClients
import com.linecorp.armeria.server.Server
import org.cyk.armeria.grpc.hello.Hello.HelloReq
import org.cyk.armeria.grpc.hello.HelloServiceGrpc.HelloServiceBlockingStub
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
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