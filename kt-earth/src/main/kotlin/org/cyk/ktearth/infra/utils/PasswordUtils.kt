package org.cyk.ktearth.infra.utils

import org.springframework.util.DigestUtils
import org.springframework.util.StringUtils
import java.util.*
import java.util.regex.Pattern
 
object PasswordUtils {
 
    /**
     * 加盐并生成密码
     * @param password 用户输入的明文密码
     * @return 保存到数据库中的密码
     */
    fun encrypt(password: String): String {
        // 产生盐值(32位)  这里要注意去掉 UUID 生成 -
        val salt = UUID.randomUUID().toString().replace("-", "")
        // 生成加盐之后的密码((盐值 + 密码)整体 md5 加密)
        val saltPassword = DigestUtils.md5DigestAsHex((salt + password).toByteArray())
        // 生成最终密码(保存到数据库中的密码)[自己约定的格式：32位盐值 +&+ 32位加盐后的密码]
        // 这样约定格式是为了解密的时候方便得到盐值
        val finalPassword = "$salt$$saltPassword"
        return finalPassword
    }
 
    /**
     * 加盐生成密码(方法1的重载)
     * 此方法在验证密码的适合需要(将用户输入的密码使用同样的盐值加密后对比)
     * @param password 明文
     * @param salt 固定的盐值
     * @return 最终密码
     */
    fun encrypt(password: String, salt: String): String {
        // 生成加盐后的密码
        val saltPassword = DigestUtils.md5DigestAsHex((salt + password).toByteArray())
        // 生成最终密码(约定格式: 32位 盐值 +&+ 32位加盐后的密码)
        val finalPassword = "$salt$$saltPassword"
        return finalPassword
    }
 
    /**
     * 验证密码
     * @param inputPassword 用户输入明文密码
     * @param dbPassword 数据库中保存的最终密码
     * @return
     */
    fun isOk(inputPassword: String, dbPassword: String): Boolean {
        // 判空处理
        if (StringUtils.hasLength(inputPassword) && StringUtils.hasLength(dbPassword) &&
            dbPassword.length == 65) {
            // 得到盐值(之前约定: $前面的就是盐值)
            val salt = dbPassword.split(Pattern.compile("\\$"))[0] // 由于 $ 在这里也可以表示通配符，所以需要使用 \\ 进行转义
            // 使用之前的加密步骤将明文进行加密，生成最终密码
            val confirmPassword = encrypt(inputPassword, salt)
            // 对比两个最终密码是否相同
            return confirmPassword == dbPassword
        }
        return false
    }
}