package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.user.domain.UserAvatarAudit
import org.cyk.ktearth.domain.user.repo.UserAvatarAuditRepo
import org.cyk.ktearth.domain.user.repo.UserAvatarRepo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component

data class AuditAvatarCmd(
    val ok: Boolean,
    val userId: String,
)

@Component
class AdminAuditAvatarHandler(
    private val userAvatarAuditRepo: UserAvatarAuditRepo,
    private val userInfoRepo: UserInfoRepo,
    private val userAvatarRepo: UserAvatarRepo,
): ApplicationHandler<AuditAvatarCmd, Unit> {

    override fun handler(input: AuditAvatarCmd) {
        // 审核信息必须存在
        val avatarAudit = userAvatarAuditRepo.queryById(input.userId)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "该审核头像不存在  id: ${input.userId}")
        // 删除对应的审核信息
        userAvatarAuditRepo.removeById(input.userId)

        if (input.ok) {
            auditSuccess(input.userId, avatarAudit)
        } else {
            auditFail(avatarAudit)
        }

    }


    /**
     * 审核成功: 删除旧头像，修改为新头像
     */
    private fun auditSuccess(userId: String, avatarAudit: UserAvatarAudit) {
        val userinfo = userInfoRepo.queryById(userId)!!

        userinfo.avatar?.let { userAvatarRepo.delByAvatar(it) }
        userinfo.avatar = avatarAudit.avatar

        userInfoRepo.update(userinfo)
    }

    /**
     * 审核失败: 删除新头像
     */
    private fun auditFail(avatarAudit: UserAvatarAudit) {
        //删除新头像
        userAvatarRepo.delByAvatar(avatarAudit.avatar)
    }

}