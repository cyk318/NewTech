package org.cyk.ktearth.facade.user

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.cyk.ktearth.application.user.*
import org.cyk.ktearth.infra.model.ApiResp
import org.cyk.ktearth.infra.model.ApiStatus
import org.cyk.ktearth.infra.utils.UserTokenUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * 用户
 */
@RestController
@RequestMapping("/user/info")
class UserInfoApi(
    private val updateAvatarHandler: UpdateAvatarHandler,
    private val queryUserInfoHandler: QueryUserInfoHandler,
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

    /**
     * id查询
     */
    @GetMapping("/query")
    fun query(
        request: HttpServletRequest,
        @RequestParam(required = false, defaultValue = "") id: String
    ): ApiResp<UserInfoVo> {
        val cmd = QueryUserInfoCmd(
            id = id.ifBlank { UserTokenUtils.getUserIdByRequest(request) }
        )
        val vo = queryUserInfoHandler.handler(cmd)
        return ApiResp.ok(vo)
    }

}

data class UpdateAvatarDto (
    @field:NotNull
    val avatar: MultipartFile,
)