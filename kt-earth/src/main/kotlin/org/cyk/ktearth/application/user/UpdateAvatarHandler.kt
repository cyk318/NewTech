package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.domain.UserAvatarAudit
import org.cyk.ktearth.domain.user.repo.UserAvatarAuditRepo
import org.cyk.ktearth.domain.user.repo.UserAvatarRepo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

data class UpdateAvatarCmd (
    val userId: String,
    val avatar: MultipartFile,
)

@Component
class UpdateAvatarHandler(
    private val userAvatarAuditRepo: UserAvatarAuditRepo,
    private val userInfoRepo: UserInfoRepo,
    private val userAvatarRepo: UserAvatarRepo,
): ApplicationHandler<UpdateAvatarCmd, Unit> {

    override fun handler(input: UpdateAvatarCmd) {
        // 用户必须存在
        userInfoRepo.queryById(input.userId)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "用户不存在  userId: ${input.userId}")
        // 该用户的头像审核信息不能存在
        val existAudit = userAvatarAuditRepo.exists(input.userId)
        if (existAudit) {
            throw AppException(ApiStatus.INVALID_REQUEST, "该用户的头像还在审核中  userId: ${input.userId}")
        }
        // 保存图片的到 MinIO
        val avatarPath = userAvatarRepo.save(input.avatar)
        // 新增头像审核信息
        val o = UserAvatarAudit(
            userId = input.userId,
            avatar =  avatarPath,
        )
        userAvatarAuditRepo.save(o)
    }

}



