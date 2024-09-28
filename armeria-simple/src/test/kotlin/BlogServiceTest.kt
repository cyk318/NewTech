import com.linecorp.armeria.client.grpc.GrpcClients
import com.linecorp.armeria.server.Server
import example.armeria.blog.grpc.BlogServiceGrpc.BlogServiceBlockingStub
import example.armeria.blog.grpc.CreateBlogPostRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class BlogServiceTest {

    private lateinit var stub: BlogServiceBlockingStub
    private lateinit var server: Server

    @BeforeEach
    fun beforeEach() {
        this.server = ArmeriaGrpcBean.newServer(9000)
        this.server.start()
        //这里启动不是异步的，所以不用 Thread.sleep 等待
        this.stub = GrpcClients.newClient(
            "http://127.0.0.1:9000/",
            BlogServiceBlockingStub::class.java,
        )
    }

    @Test
    fun createBlogTest() {
        val req = CreateBlogPostRequest.newBuilder()
            .setTitle("我的博客1")
            .setContent("今天天气真不错~")
            .build()

        println("================= req =================")
        val resp = stub.createBlogPost(req)
        println(resp)
        println("================= resp =================")
    }

}