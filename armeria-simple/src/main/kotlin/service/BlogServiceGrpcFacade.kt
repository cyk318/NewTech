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
        val id = idGenerator.getAndIncrement()
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

    override fun queryBlogByIds(
        request: QueryBlogByIdsReq,
        responseObserver: StreamObserver<QueryBlogByIdsResp>,
    ) {
        val blogs = blogRepo.filter {
            return@filter request.idsList.contains(it.key)
        }.map { it.value }

        val resp = QueryBlogByIdsResp.newBuilder()
            .addAllBlogs(blogs)
            .build()
        responseObserver.onNext(resp)
        responseObserver.onCompleted()
    }

    override fun updateBlogById(
        request: UpdateBlogByIdReq,
        responseObserver: StreamObserver<UpdateBlogResp>
    ) {
        //这里的校验一般不再这一层做(还会有 Handler 读写分离类)
        val (errorMsg, beforeBlog) = checkAndGetPair(request)
        if (errorMsg != null) {
            responseObserver.onNext(
                UpdateBlogResp.newBuilder()
                .setErrorMsg(errorMsg)
                .build()
            )
            responseObserver.onCompleted()
            return
        }

        val afterBlog = Blog.newBuilder().apply {
            id = beforeBlog!!.id
            title = request.title
            content = request.content
        }.build()
        blogRepo[afterBlog.id] = afterBlog

        val resp = UpdateBlogResp.newBuilder()
            .setBlog(afterBlog)
            .build()
        responseObserver.onNext(resp)
        responseObserver.onCompleted()
    }

    private fun checkAndGetPair(req: UpdateBlogByIdReq): Pair<String?, Blog?> {
        val blog = blogRepo[req.id]
            ?: return "文章不存在" to null
        // 如果还需要其他校验
        // ...
        return null to blog
    }

}