package org.cyk.ktearth.application.oss

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.oss.domain.FileUploadBiz
import org.cyk.ktearth.domain.oss.domain.FileUploadBizCmd
import org.cyk.ktearth.domain.oss.repo.OssFileRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

data class AdminFileUploadCmd (
    val userId: String,
    val biz: Int,
    val file: MultipartFile,
)

@Component
class AdminFileUploadHandler(
    private val ossFileRepo: OssFileRepo,
): ApplicationHandler<AdminFileUploadCmd, String> {

    override fun handler(input: AdminFileUploadCmd): String {
        val biz = FileUploadBiz.of(input.biz)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "非法 biz: ${input.biz}")

        //根据不同 biz类型 处理不同的 bucket
        return biz.upload(
            FileUploadBizCmd (
                userId = input.userId,
                file = input.file,
                ossFileRepo = ossFileRepo,
            )
        )
    }

}