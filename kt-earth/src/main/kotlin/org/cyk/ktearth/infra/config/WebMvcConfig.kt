package org.cyk.ktearth.infra.config

import org.cyk.ktearth.infra.aop.LoginInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val loginInterceptor: LoginInterceptor,
): WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/admin/auth/reg")
            .excludePathPatterns("/user/auth/login")
    }

}