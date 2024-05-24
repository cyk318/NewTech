package org.cyk.client.api

import net.devh.boot.grpc.client.inject.GrpcClient
import org.cyk.HelloProto
import org.cyk.HelloProto.HelloRequest
import org.cyk.HelloServiceGrpc.HelloServiceBlockingStub
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloApi {

    @GrpcClient("client")
    private lateinit var stub: HelloServiceBlockingStub

    @GetMapping
    fun hello(msg: String): String {
        val request = HelloRequest.newBuilder()
            .setMsg(msg)
            .build()
        val response = stub.hello(request)
        return response.result
    }


}