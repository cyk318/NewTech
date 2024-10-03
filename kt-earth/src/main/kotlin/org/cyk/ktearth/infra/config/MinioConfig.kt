package org.cyk.ktearth.infra.config

//import io.minio.MinioClient
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class MinioConfig {
//
//    @Value("\${minio.endpoint}")
//    private lateinit var endpoint: String
//
//    @Value("\${minio.username}")
//    private lateinit var username: String
//
//    @Value("\${minio.password}")
//    private lateinit var password: String
//
//    @Bean
//    fun minioClient(): MinioClient = MinioClient.builder()
//        .endpoint(endpoint)
//        .credentials(username, password)
//        .build()
//
//}