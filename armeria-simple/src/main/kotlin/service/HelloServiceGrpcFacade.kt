package service

import io.grpc.stub.StreamObserver
import org.cyk.armeria.grpc.hello.Hello
import org.cyk.armeria.grpc.hello.Hello.HelloResp
import org.cyk.armeria.grpc.hello.HelloServiceGrpc.HelloServiceImplBase

class HelloServiceGrpcFacade: HelloServiceImplBase() {

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