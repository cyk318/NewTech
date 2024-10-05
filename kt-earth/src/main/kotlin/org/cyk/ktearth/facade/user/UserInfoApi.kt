package org.cyk.ktearth.facade.user

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.cyk.ktearth.application.user.UpdateAvatarCmd
import org.cyk.ktearth.application.user.UpdateAvatarHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * 用户
 */
@RestController
@RequestMapping("/user/info")
class UserInfoApi(
    private val updateAvatarHandler: UpdateAvatarHandler,
) {

    /**
     * 修改头像
     */
    @PostMapping("/update/avatar")
    fun updateAvatar(
        request: HttpServletRequest,
        @ModelAttribute @Valid dto: UpdateAvatarDto,
    ): ApiResp<Unit> {
        val cmd = UpdateAvatarCmd(
            userId = UserTokenUtils.getUserIdByRequest(request),
            avatar = dto.avatar,
        )
        updateAvatarHandler.handler(cmd)
        return ApiResp.ok()
    }

}

data class UpdateAvatarDto (
    @field:NotNull
    val avatar: MultipartFile,
)