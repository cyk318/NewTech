package org.cyk.ktearth.domain.oss.repo

import org.springframework.web.multipart.MultipartFile

interface OssFileRepo {

    /**
     * 保存图片文件
     * @param file 图片文件
     */
    fun save(bucket: String, userId: String, file: MultipartFile): String

    /**
     * 删除图片文件
     * @param file 图片文件路径
     */
    fun remove(bucket: String, userId: String, file: String)

}