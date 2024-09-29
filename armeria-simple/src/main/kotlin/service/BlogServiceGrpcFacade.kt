package service

import io.grpc.stub.StreamObserver
import org.cyk.armeria.grpc.blog.*
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class BlogServiceGrpcFacade: BlogServiceGrpc.BlogServiceImplBase() {

    // ID 生成器
    private val idGenerator = AtomicInteger()
    // 这里为了专注 Armeria-gRPC 的处理，使用 map 来替代数据库
    private val blogRepo = ConcurrentHashMap<Int, Blog>()

    override fun createBlog(
        request: CreateBlogReq,
        responseObserver: StreamObserver<CreateBlogResp>
    ) {
        val id = idGenerator.getAndDecrement()
        val now = Instant.now()

        val blog = Blog.newBuilder()
            .setId(id)
            .setTitle(request.title)
            .setContent(request.content)
            .setModifiedAt(now.toEpochMilli())
            .setCreatedAt(now.toEpochMilli())
            .build()
        blogRepo[id] = blog

        val resp = CreateBlogResp.newBuilder()
            .setBlog(blog)
            .build()
        responseObserver.onNext(resp)
        responseObserver.onCompleted()
    }

    override fun queryBlogById(
        request: QueryBlogByIdReq,
        responseObserver: StreamObserver<QueryBlogByIdResp>
    ) {
        val resp = QueryBlogByIdResp.newBuilder()

        blogRepo[request.id]?.let {
            //这里的 it 不能为 null (proto 编译出的文件，只要 set，就不能为 null，除非你不 set)
            resp.setBlog(it)
        }

//        如果不习惯, 可以对可能为空的字段滞后处理
//        val blog = blogRepo[request.id]
//        val resp = QueryBlogByIdResp.newBuilder().apply {
//            blog?.let { setBlog(it) }
//        }

        responseObserver.onNext(resp.build())
        responseObserver.onCompleted()
    }

}