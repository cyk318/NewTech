package org.cyk.server.service

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.cyk.HelloProto
import org.cyk.HelloProto.HelloResponse
import org.cyk.HelloServiceGrpc

@GrpcService
class HelloService: HelloServiceGrpc.HelloServiceImplBase() {

    override fun hello(request: HelloProto.HelloRequest, responseObserver: StreamObserver<HelloProto.HelloResponse>) {
        val msg = request.msg
        println("收到客户端请求: $msg")
        val response = HelloResponse.newBuilder()
            .setResult("$msg ok~")
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

}