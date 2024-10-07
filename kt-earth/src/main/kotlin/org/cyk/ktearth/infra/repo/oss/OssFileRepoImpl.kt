package org.cyk.ktearth.infra.repo.oss

import io.minio.*
import org.cyk.ktearth.domain.oss.repo.OssFileRepo
import org.cyk.ktearth.infra.utils.MinIOFileUtils
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile

@Repository
class OssFileRepoImpl(
    private val minioClient: MinioClient,
): OssFileRepo {

    override fun save(bucket: String, userId: String, file: MultipartFile): String {
        makeBucketIfNotExists(bucket)

        val fileName = MinIOFileUtils.generateFileNameByMultipartFile(file)
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`("$userId/$fileName")
                .stream(file.inputStream, file.size, -1)
                .build()
        )
        return "/$bucket/$userId/$fileName"
    }


    override fun remove(bucket: String, userId: String, file: String) {
        val fileName = MinIOFileUtils.parsePathToFileName(file)
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucket)
                .`object`("$userId/$fileName")
                .build()
        )
    }

    /**
     * 如果 bucket 不存在就创建(可读)
     */
    private fun makeBucketIfNotExists(bucket: String) {
        val bucketExists = minioClient.bucketExists(
            BucketExistsArgs.builder()
                .bucket(bucket)
                .build()
        )
        if (!bucketExists) {
            minioClient.makeBucket(
                MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build()
            )

            // 设置存储桶为公共可读
            val policy = """
                    {
                        "Version": "2012-10-17",
                        "Statement": [
                            {
                                "Effect": "Allow",
                                "Principal": "*",
                                "Action": ["s3:GetObject"],
                                "Resource": ["arn:aws:s3:::${bucket}/*"]
                            }
                        ]
                    }
                """.trimIndent()

            minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                    .bucket(bucket)
                    .config(policy)
                    .build()
            )
        }
    }

}