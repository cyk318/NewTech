
import com.linecorp.armeria.client.DecoratingHttpClientFunction
import com.linecorp.armeria.client.Endpoint
import com.linecorp.armeria.client.endpoint.EndpointGroup
import com.linecorp.armeria.client.endpoint.EndpointSelectionStrategy
import com.linecorp.armeria.client.grpc.GrpcClients
import org.cyk.armeria.grpc.hello.Hello.HelloReq
import org.cyk.armeria.grpc.hello.HelloServiceGrpc.HelloServiceBlockingStub
import kotlin.test.Test

class GrpcLoadBalancingTests {

    @Test
    fun test() {
        // 定义多个服务实例
        // 默认负载均衡策略为: EndpointSelectionStrategy.weightedRoundRobin() -> 加权轮询，每个实例的权重默认是 1000
        val instanceGroup = EndpointGroup.of(
            Endpoint.of("localhost", 9001),
            Endpoint.of("localhost", 9002),
        )

        val clientLB = GrpcClients.builder("gproto+http://group/")
            .endpointRemapper { instanceGroup }
            .build(HelloServiceBlockingStub::class.java)

        val req = HelloReq.newBuilder()
            .setName("cyk")
            .build()

        for (i in 1..3) {
            val resp = clientLB.hello(req)
            val expect = "hello cyk ~"
            require(resp.msg == expect ) { "expect: $expect, actual: ${resp.msg} "}
        }

    }

    @Test
    fun testCustom() {
        //使用加权负载均衡策略，默认权重为 1000
        val strategy = EndpointSelectionStrategy.weightedRoundRobin() // 这也是默认策略
        val instanceGroup = EndpointGroup.of(
            strategy,
            Endpoint.of("localhost", 9001).withWeight(1),
            Endpoint.of("localhost", 9002).withWeight(9),
        )

        var c9001 = 0
        var c9002 = 0

        // 客户端装饰器: 记录调用次数
        // Ps：这里偷了个懒，建议还是专门弄个类，然后实现 DecoratingHttpClientFunction 接口
        val decorator = DecoratingHttpClientFunction { delegate, ctx, req ->
            if (ctx.endpoint()!!.port() == 9001) {
                c9001++
            } else {
                c9002++
            }
            return@DecoratingHttpClientFunction delegate.execute(ctx, req)
        }

        val clientLB = GrpcClients.builder("gproto+http://group/")
            .endpointRemapper { instanceGroup }
            .decorator(decorator)
            .build(HelloServiceBlockingStub::class.java)

        val req = HelloReq.newBuilder()
            .setName("cyk")
            .build()

        for (i in 1..100) {
            val resp = clientLB.hello(req)
            val expect = "hello cyk ~"
            require(resp.msg == expect ) { "expect: $expect, actual: ${resp.msg} "}
        }

        println("9001调用次数: $c9001")
        println("9002调用次数: $c9002")

    }

    @Test
    fun testNacosLB() {
        // 从 nacos 中获取 helloGrpcService 服务下所有 健康 的服务实例
        val endpointGroup = NacosBean.newService()
            .selectInstances("helloGrpcService", "DEFAULT", true) // healthy: true
            .map { Endpoint.of(it.ip, it.port) }
            .let { endpoints ->
                EndpointGroup.of(
                    EndpointSelectionStrategy.roundRobin(),
                    endpoints
                )
            }

        val clientLB = GrpcClients.builder("gproto+http://group/")
            .endpointRemapper { endpointGroup }
            .decorator(
                DecoratingHttpClientFunction { delegate, ctx, req ->
                    println("目标端点: ${ctx.endpoint()!!.port()}")
                    return@DecoratingHttpClientFunction delegate.execute(ctx, req)
                }
            )
            .build(HelloServiceBlockingStub::class.java)
        val req = HelloReq.newBuilder()
            .setName("cyk")
            .build()
        for (i in 0..10) {
            val resp = clientLB.hello(req)
            val expect = "hello cyk ~"
            require(resp.msg == expect ) { "expect: $expect, actual: ${resp.msg} "}
        }
    }

}