package service

import example.armeria.blog.grpc.BlogPost
import example.armeria.blog.grpc.BlogServiceGrpc.BlogServiceImplBase
import example.armeria.blog.grpc.CreateBlogPostRequest
import io.grpc.stub.StreamObserver
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class BlogService: BlogServiceImplBase() {

    // ID 生成器
    private val idGenerator = AtomicInteger()
    // 这里为了专注 Armeria-gRPC 的处理，使用 map 来替代数据库
    private val blogRepo = ConcurrentHashMap<Int, BlogPost>()

    override fun createBlogPost(
        request: CreateBlogPostRequest,
        responseObserver: StreamObserver<BlogPost>
    ) {
        val id = idGenerator.getAndDecrement()
        val now = Instant.now()

        val blog = BlogPost.newBuilder()
            .setId(id)
            .setTitle(request.title)
            .setContent(request.content)
            .setModifiedAt(now.toEpochMilli())
            .setCreatedAt(now.toEpochMilli())
            .build()
        blogRepo[id] = blog

        responseObserver.onNext(blog)
        responseObserver.onCompleted()
    }

}