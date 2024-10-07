package org.cyk.ktearth.facade.oss

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.cyk.ktearth.application.oss.AdminFileUploadCmd
import org.cyk.ktearth.application.oss.AdminFileUploadHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/admin/file/upload")
class AdminFileUploadApi(
    private val adminFileUploadHandler: AdminFileUploadHandler,
) {

    @PostMapping
    fun upload(
        request: HttpServletRequest,
        @ModelAttribute dto: AdminFileUploadDto,
    ): ApiResp<String> {
        val cmd = AdminFileUploadCmd (
            userId = UserTokenUtils.getUserIdByRequest(request),
            biz = dto.biz,
            file = dto.file,
        )
        val filePath = adminFileUploadHandler.handler(cmd)
        return ApiResp.ok(filePath)
    }

}

data class AdminFileUploadDto (
    @field:NotBlank
    val biz: Int,
    @field:NotNull
    val file: MultipartFile,
)


