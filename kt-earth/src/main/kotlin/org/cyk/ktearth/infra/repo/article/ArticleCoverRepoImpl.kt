package org.cyk.ktearth.infra.repo.article

import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

//@Repository
//class ArticleCoverRepoImpl(
//    private val minioClient: MinioClient
//): ArticleCoverRepo {
//
//    @Value("\${minio.bucket.article.cover}")
//    private lateinit var bucket: String
//
//    override fun save(cover: MultipartFile): String {
//        val fileName = generateFileNameByMultipartFile(cover)
//        minioClient.putObject(
//            PutObjectArgs.builder()
//                .bucket(bucket)
//                .`object`(fileName)
//                .stream(cover.inputStream, cover.size, -1)
//                .build()
//        )
//        return "/$bucket/$fileName"
//    }
//
//    override fun delByCover(cover: String) {
//        val fileName = parseCoverToFileName(cover)
//        minioClient.removeObject(
//            RemoveObjectArgs.builder()
//                .bucket(bucket)
//                .`object`(fileName)
//                .build()
//        )
//    }
//
//    /**
//     * @param cover: 文件具体存储路径
//     * @return 文件名
//     */
//    private fun parseCoverToFileName(cover: String): String {
//        val idx = cover.lastIndexOf('/')
//        return cover.substring(idx + 1)
//    }
//
//    private fun generateFileNameByMultipartFile(cover: MultipartFile): String {
//        val fileType = cover.originalFilename!!
//            .indexOfLast { it == '.' }
//            .let { idx -> return@let cover.originalFilename!!.substring(idx + 1) }
//        return "${UUID.randomUUID()}.$fileType"
//    }
//
//}