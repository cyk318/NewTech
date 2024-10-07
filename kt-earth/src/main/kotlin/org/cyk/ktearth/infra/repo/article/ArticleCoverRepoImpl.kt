package org.cyk.ktearth.infra.repo.article

import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.cyk.ktearth.domain.article.repo.ArticleCoverRepo
import org.cyk.ktearth.infra.utils.MinIOFileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile

@Repository
class ArticleCoverRepoImpl(
    private val minioClient: MinioClient
): ArticleCoverRepo {

    @Value("\${minio.bucket.article.cover}")
    private lateinit var bucket: String

    override fun save(cover: MultipartFile): String {
        val fileName = MinIOFileUtils.generateFileNameByMultipartFile(cover)
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(fileName)
                .stream(cover.inputStream, cover.size, -1)
                .build()
        )
        return "/$bucket/$fileName"
    }

    override fun removeByCover(cover: String) {
        val fileName = MinIOFileUtils.parsePathToFileName(cover)
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucket)
                .`object`(fileName)
                .build()
        )
    }

}