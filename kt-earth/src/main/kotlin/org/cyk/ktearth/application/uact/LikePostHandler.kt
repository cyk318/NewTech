package org.cyk.ktearth.application.uact

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.uact.domain.Like
import org.cyk.ktearth.domain.uact.domain.LikeType
import org.cyk.ktearth.domain.uact.domain.UserLikes
import org.cyk.ktearth.domain.uact.repo.UserLikesRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component

data class LikePostCmd(
    val userId: String,
    val targetId: String,
    val type: Int,
)

@Component
class LikePostHandler(
    private val userLikesRepo: UserLikesRepo,
    private val articleInfoRepo: ArticleInfoRepo,
): ApplicationHandler<LikePostCmd, Int> {

    override fun handler(input: LikePostCmd): Int {
        checkValid(input)
        val userLikes = userLikesRepo.queryByUserId(input.userId)
        val likeType = LikeType.codeOf(input.type)!!

        //点赞的目标必须存在
        val exists = when(likeType) {
            LikeType.ARTICLE -> articleInfoRepo.exists(input.targetId)
            LikeType.COMMENT -> TODO()
            else -> false
        }
        if (!exists) {
            throw AppException(ApiStatus.INVALID_REQUEST, "点赞的目标不存在  $input")
        }

        //UserLike 不存在，就新增
        if (userLikes == null) {
            userLikesRepo.save(UserLikes(
                userId = input.userId,
                likes = mutableListOf(Like(input.targetId, likeType))
            ))
            return 1
        }

        //点赞逻辑: Like 存在就删除，不存在就新增
        val likeStatus = userLikes.submitLike(input.targetId, LikeType.codeOf(input.type)!!)
        userLikesRepo.save(userLikes)
        return likeStatus
    }

    private fun checkValid(input: LikePostCmd) {
        if (input.userId.isBlank()) {
            throw AppException(ApiStatus.INVALID_REQUEST, "用户名不能为空")
        }
        if (input.targetId.isBlank()) {
            throw AppException(ApiStatus.INVALID_REQUEST, "点赞的目标不能为空")
        }
        LikeType.codeOf(input.type)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "类型不存在  type: ${input.type}")
    }

}