package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.oss.repo.OssFileRepo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

data class AdminUploadCoverCmd (
    val userId: String,
    val file: MultipartFile,
)

@Component
class AdminUploadCoverHandler(
    private val ossFileRepo: OssFileRepo,
): ApplicationHandler<AdminUploadCoverCmd, String> {

    @Value("\${minio.bucket.article.cover}")
    private lateinit var bucket: String

    override fun handler(input: AdminUploadCoverCmd): String {
        return ossFileRepo.save(
            bucket = bucket,
            userId = input.userId,
            file = input.file,
        )
    }

}

