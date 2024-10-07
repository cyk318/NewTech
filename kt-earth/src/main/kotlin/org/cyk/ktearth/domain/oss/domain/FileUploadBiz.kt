package org.cyk.ktearth.domain.oss.domain

import org.cyk.ktearth.domain.oss.repo.OssFileRepo
import org.springframework.web.multipart.MultipartFile

data class FileUploadBizCmd (
    val userId: String,
    val file: MultipartFile,
    val ossFileRepo: OssFileRepo,
)

enum class FileUploadBiz(
    private val code: Int,
    private val bucket: String,
) {

    /**
     * 用户头像
     */
    USER_AVATAR(1, "user-avatar"),

    /**
     * 文章封面
     */
    ARTICLE_COVER(2, "article-cover"),
    ;

    companion object {
        fun of(code: Int) = entries.firstOrNull { it.code == code }
    }

    fun upload(cmd: FileUploadBizCmd): String {
        return cmd.ossFileRepo.save(
            bucket = this.bucket,
            userId = cmd.userId,
            file = cmd.file
        )
    }

}
