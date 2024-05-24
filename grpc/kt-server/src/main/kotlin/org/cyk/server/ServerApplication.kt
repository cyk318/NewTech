package org.cyk.server

import net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration
import net.devh.boot.grpc.common.autoconfigure.GrpcCommonTraceAutoConfiguration
import net.devh.boot.grpc.server.autoconfigure.*
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@ImportAutoConfiguration(
    GrpcCommonCodecAutoConfiguration::class,
    GrpcCommonTraceAutoConfiguration::class,
    GrpcMetadataConsulConfiguration::class,
    GrpcMetadataEurekaConfiguration::class,
    GrpcMetadataNacosConfiguration::class,
    GrpcServerAutoConfiguration::class,
    GrpcServerFactoryAutoConfiguration::class,
    GrpcServerMetricAutoConfiguration::class,
    GrpcServerSecurityAutoConfiguration::class,
    GrpcServerTraceAutoConfiguration::class
)
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}