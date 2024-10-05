package org.cyk.ktearth.infra.utils

import org.springframework.web.multipart.MultipartFile
import java.util.*

object MinIOFileUtils {

    /**
     * 根据 MultipartFile 文件 生成 文件全名
     */
    fun generateFileNameByMultipartFile(image: MultipartFile): String {
        val fileType = image.originalFilename!!
            .indexOfLast { it == '.' }
            .let { idx -> return@let image.originalFilename!!.substring(idx + 1) }
        return "${UUID.randomUUID()}.$fileType"
    }

    /**
     * 解析路径
     * 例如
     * input: /article-cover/33cbc431-0083-4a74-8829-39ea5d3e0dc2.png
     * output: 33cbc431-0083-4a74-8829-39ea5d3e0dc2.png
     */
    fun parsePathToFileName(cover: String): String {
        val idx = cover.lastIndexOf('/')
        return cover.substring(idx + 1)
    }

}