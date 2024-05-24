package org.cyk.server

import net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration
import net.devh.boot.grpc.common.autoconfigure.GrpcCommonTraceAutoConfiguration
import net.devh.boot.grpc.server.autoconfigure.*
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@ImportAutoConfiguration(
//    GrpcCommonCodecAutoConfiguration::class, //gRPC 解码器，用于服务器和客户端通讯
//    GrpcCommonTraceAutoConfiguration::class, //追踪功能，用于 gRPC 服务器跟踪，以便监视请求和响应的流程性能
//    GrpcMetadataNacosConfiguration::class, //集成 Nacos

    //一般来讲，以下两个自动配置类就够了
    GrpcServerAutoConfiguration::class, //用于自动配置 gRPC 服务 ip 和 port
    GrpcServerFactoryAutoConfiguration::class, //自动配置 gRPC 服务器工厂，根据需要创建 gRPC 服务器实例

//    GrpcServerMetricAutoConfiguration::class, //集成度量指标, 允许在 gRPC 服务器端收集和暴露关于请求处理情况的指标。
//    GrpcServerSecurityAutoConfiguration::class, //安全认证配置
//    GrpcServerTraceAutoConfiguration::class // 用于集成分布式跟踪功能
)
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}