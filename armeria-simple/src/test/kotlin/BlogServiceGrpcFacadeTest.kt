import com.linecorp.armeria.client.grpc.GrpcClients
import com.linecorp.armeria.server.Server
import org.cyk.armeria.grpc.blog.BlogServiceGrpc
import org.cyk.armeria.grpc.blog.CreateBlogReq
import org.cyk.armeria.grpc.blog.QueryBlogByIdReq
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class BlogServiceGrpcFacadeTest {


    companion object {

        private lateinit var stub: BlogServiceGrpc.BlogServiceBlockingStub
        private lateinit var server: Server
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            server = ArmeriaGrpcBean.newServer(9000)
            server.start()
            //这里启动不是异步的，所以不用 Thread.sleep 等待
            stub = GrpcClients.newClient(
                "http://127.0.0.1:9000/",
                BlogServiceGrpc.BlogServiceBlockingStub::class.java,
            )
        }
    }

    @Test
    @Order(1)
    fun createBlogTest() {
        val req = CreateBlogReq.newBuilder()
            .setTitle("我的博客1")
            .setContent("今天天气真不错~")
            .build()

        println("================= req send ... =================")
        val resp = stub.createBlog(req)
        println(resp.blog.title)
        println(resp.blog.content)
        println("================= resp received ... =================")
    }

    @Test
    @Order(2)
    fun queryBlogByIdTest() {
        val req = QueryBlogByIdReq.newBuilder()
            .setId(0)
            .build()
        println("================= req send ... =================")
        val resp = stub.queryBlogById(req)
        if (resp.hasBlog()) {
            println(resp.blog.title)
            println(resp.blog.content)
        }
        println("================= req received ... =================")
    }

}