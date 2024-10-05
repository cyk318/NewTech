package org.cyk.ktearth.domain.user.repo

import org.springframework.web.multipart.MultipartFile


interface UserAvatarRepo {

    /**
     * 根据 MultipartFile 保存头像
     * @return 路径
     */
    fun save(avatar: MultipartFile): String

    /**
     * 根据头像路径删除头像照片
     */
    fun delByAvatar(avatar: String)

}