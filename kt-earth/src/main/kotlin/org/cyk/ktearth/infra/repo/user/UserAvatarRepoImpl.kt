package org.cyk.ktearth.infra.repo.user

import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.cyk.ktearth.domain.user.repo.UserAvatarRepo
import org.cyk.ktearth.infra.utils.MinIOFileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile

@Repository
class UserAvatarRepoImpl(
    private val minioClient: MinioClient,
): UserAvatarRepo {

    @Value("\${minio.bucket.user.avatar}")
    private lateinit var bucket: String

    override fun save(avatar: MultipartFile): String {
        val fileName = MinIOFileUtils.generateFileNameByMultipartFile(avatar)
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(fileName)
                .stream(avatar.inputStream, avatar.size, -1)
                .build()
        )
        return "/$bucket/$fileName"
    }

    override fun delByAvatar(avatar: String) {
        val fileName = MinIOFileUtils.parsePathToFileName(avatar)
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucket)
                .`object`(fileName)
                .build()
        )
    }

}