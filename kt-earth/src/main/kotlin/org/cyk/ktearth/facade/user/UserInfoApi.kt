package org.cyk.ktearth.facade.user

import jakarta.servlet.http.HttpServletRequest
import org.cyk.ktearth.application.user.UpdateAvatarCmd
import org.cyk.ktearth.application.user.UpdateAvatarHandler
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/user/info")
class UserInfoApi(
    private val updateAvatarHandler: UpdateAvatarHandler,
) {

    @PostMapping("/update/avatar")
    fun updateAvatar(
        request: HttpServletRequest,
        @ModelAttribute dto: UpdateAvatarDto,
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
    val avatar: MultipartFile,
)