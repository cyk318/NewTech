package service

import com.linecorp.armeria.server.annotation.Decorator
import component.Custom2
import io.grpc.stub.StreamObserver
import org.cyk.armeria.grpc.hello.Hello
import org.cyk.armeria.grpc.hello.Hello.HelloResp
import org.cyk.armeria.grpc.hello.HelloServiceGrpc.HelloServiceImplBase

@Decorator(Custom2::class) // 👈👈👈 对该类下的所有方法都管用
class HelloServiceGrpcFacade: HelloServiceImplBase() {

    @Decorator(Custom2::class) // 👈👈👈 仅对该方法管用
    override fun hello(
        request: Hello.HelloReq,
        responseObserver: StreamObserver<HelloResp>
    ) {
        val resp = HelloResp.newBuilder()
            .setMsg("hello ${request.name} ~")
            .build()
        responseObserver.onNext(resp)
        responseObserver.onCompleted()
    }

}