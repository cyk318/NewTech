import com.linecorp.armeria.client.grpc.GrpcClients
import com.linecorp.armeria.server.Server
import example.armeria.blog.grpc.BlogServiceGrpc.BlogServiceBlockingStub
import example.armeria.blog.grpc.CreateBlogPostRequest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class BlogServiceTest {

    companion object {

        private lateinit var stub: BlogServiceBlockingStub
        private lateinit var server: Server
        private val log = LoggerFactory.getLogger(BlogServiceTest::class.java)

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            this.server = ArmeriaGrpcBean.newServer(9000)
            this.server.start()

            this.stub = GrpcClients.newClient(
                "http://127.0.0.1:9000/",
                BlogServiceBlockingStub::class.java,
            )

            Thread.sleep(3000) //等服务启动完成
        }

    }

    @Test
    fun createBlogTest() {
        val req = CreateBlogPostRequest.newBuilder()
            .setTitle("我的博客1")
            .setContent("今天天气真不错~")
            .build()
        val resp = stub.createBlogPost(req)
        println(resp)
    }

}