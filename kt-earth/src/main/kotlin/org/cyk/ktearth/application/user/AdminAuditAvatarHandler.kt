package org.cyk.ktearth.application.user

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.oss.repo.OssFileRepo
import org.cyk.ktearth.domain.user.domain.UserAvatarAudit
import org.cyk.ktearth.domain.user.repo.UserAvatarAuditRepo
import org.cyk.ktearth.domain.user.repo.UserInfoRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

data class AuditAvatarCmd(
    val ok: Boolean,
    val userId: String,
)

@Component
class AdminAuditAvatarHandler(
    private val userAvatarAuditRepo: UserAvatarAuditRepo,
    private val userInfoRepo: UserInfoRepo,
    private val ossFileRepo: OssFileRepo,
): ApplicationHandler<AuditAvatarCmd, Unit> {

    @Value("\${minio.bucket.user.avatar}")
    private lateinit var bucket: String

    override fun handler(input: AuditAvatarCmd) {
        // 审核信息必须存在
        val avatarAudit = userAvatarAuditRepo.queryById(input.userId)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "该审核头像不存在  id: ${input.userId}")
        // 删除对应的审核信息
        userAvatarAuditRepo.removeById(input.userId)

        if (input.ok) {
            auditSuccess(avatarAudit)
        } else {
            auditFail(avatarAudit)
        }

    }


    /**
     * 审核成功: 删除旧头像，修改为新头像
     */
    private fun auditSuccess(avatarAudit: UserAvatarAudit) {
        val userinfo = userInfoRepo.queryById(avatarAudit.userId)!!

        userinfo.avatar?.let { ossFileRepo.remove(bucket, avatarAudit.userId, it) }
        userinfo.avatar = avatarAudit.avatar

        userInfoRepo.update(userinfo)
    }

    /**
     * 审核失败: 删除新头像
     */
    private fun auditFail(avatarAudit: UserAvatarAudit) {
        //删除新头像
        ossFileRepo.remove(bucket, avatarAudit.userId, avatarAudit.avatar)
    }

}