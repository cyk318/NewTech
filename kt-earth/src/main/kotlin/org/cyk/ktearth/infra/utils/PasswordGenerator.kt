package org.cyk.ktearth.infra.utils

object PasswordGenerator {

    // 定义密码字符集，包括大小写字母、数字和特殊字符
    private const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
    private const val DIGITS = "0123456789"
    private const val SPECIAL_CHARS = "!@#$%^&*()-_+=<>?"

    fun generateStrongPassword(length: Int): String {
        require(length >= 8) { "密码长度必须至少为 8 个字符" }

        // 确保密码至少包含大写字母、小写字母、数字和特殊字符
        val password = StringBuilder(length)
        password.append(UPPERCASE.random())
        password.append(LOWERCASE.random())
        password.append(DIGITS.random())
        password.append(SPECIAL_CHARS.random())

        // 填充剩余的字符
        val allChars = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS
        repeat(length - 4) {
            password.append(allChars.random())
        }

        // 打乱密码字符的顺序
        return password.toList().shuffled().joinToString("")
    }

}
