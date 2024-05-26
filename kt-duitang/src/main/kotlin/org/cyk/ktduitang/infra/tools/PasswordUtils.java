package org.cyk.ktduitang.infra.tools;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class PasswordUtils {

    /**
     * 加盐并生成密码
     * @param password 用户输入的明文密码
     * @return 保存到数据库中的密码
     */
    public static String encrypt(String password) {
        // 产生盐值(32位)  这里要注意去掉 UUID 生成 -
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        // 生成加盐之后的密码((盐值 + 密码)整体 md5 加密)
        String saltPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        // 生成最终密码(保存到数据库中的密码)[自己约定的格式：32位盐值 +&+ 32位加盐后的密码]
        // 这样约定格式是为了解密的时候方便得到盐值
        String finalPassword = salt + "$" + saltPassword;
        return finalPassword;
    }

    /**
     * 加盐生成密码(方法1的重载)
     * 此方法在验证密码的适合需要(将用户输入的密码使用同样的盐值加密后对比)
     * @param password 明文
     * @param salt 固定的盐值
     * @return 最终密码
     */
    public static String encrypt(String password, String salt) {
        // 生成加盐后的密码
        String saltPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        // 生成最终密码(约定格式: 32位 盐值 +&+ 32位加盐后的密码)
        String finalPassword = salt + "$" + saltPassword;
        return finalPassword;
    }

    /**
     * 验证密码
     * @param inputPassword 用户输入明文密码
     * @param dbPassword 数据库中保存的最终密码
     * @return
     */
    public static boolean isOk(String inputPassword, String dbPassword) {
        // 判空处理
        if(StringUtils.hasLength(inputPassword) && StringUtils.hasLength(dbPassword) &&
                dbPassword.length() == 65) {
            // 得到盐值(之前约定: $前面的就是盐值)
            String salt = dbPassword.split("\\$")[0];// 由于 $ 在这里也可以表示通配符，所以需要使用 \\ 进行转义
            // 使用之前的加密步骤将明文进行加密，生成最终密码
            String confirmPassword = PasswordUtils.encrypt(inputPassword, salt);
            // 对比两个最终密码是否相同
            return confirmPassword.equals(dbPassword);
        }
        return false;
    }

}
