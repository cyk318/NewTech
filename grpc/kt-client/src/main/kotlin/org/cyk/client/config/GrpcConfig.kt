package org.cyk.client.config

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.cyk.HelloServiceGrpc
import org.cyk.HelloServiceGrpc.HelloServiceBlockingStub
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfig {

    @Value("\${grpc-client.host}")
    private var host: String = ""

    @Value("\${grpc-client.port}")
    private var port: Int = -1

    @Bean
    fun helloServiceBlockingStub(): HelloServiceBlockingStub {
        val managedChannel = ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .build()
        return HelloServiceGrpc.newBlockingStub(managedChannel)
    }

}