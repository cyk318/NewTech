package org.cyk.springcloudstreammq.test;

public class RegexMatcher {

    public static boolean F(String x, String y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Input strings cannot be null");
        }
        return isMatch(x, y);
    }

    private static boolean isMatch(String text, String pattern) {
        // 动态规划二维数组，dp[i][j]表示x[0..i-1]是否与y[0..j-1]匹配
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];

        // 空字符串和空字符串是匹配的
        dp[0][0] = true;

        // 处理以 '*' 开头的模式
        for (int j = 1; j < pattern.length() + 1; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        // 填充dp数组
        for (int i = 1; i < text.length() + 1; i++) {
            for (int j = 1; j < pattern.length() + 1; j++) {
                char currentPatternChar = pattern.charAt(j - 1);

                if (currentPatternChar == '.' || currentPatternChar == text.charAt(i - 1)) {
                    // '.' 或者当前字符匹配
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (currentPatternChar == '*') {
                    // '*' 可以表示0个或多个前一个字符
                    char precedingChar = pattern.charAt(j - 2);
                    dp[i][j] = dp[i][j - 2]; // '*' 匹配0个字符

                    if (precedingChar == '.' || precedingChar == text.charAt(i - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j]; // '*' 匹配1个或多个字符
                    }
                }
            }
        }

        // 返回最终结果
        return dp[text.length()][pattern.length()];
    }

    public static void main(String[] args) {
        System.out.println(F("a", ".")); // true
        System.out.println(F("aa", "a*")); // true
        System.out.println(F("ab", ".*")); // true
        System.out.println(F("aab", "c*a*b")); // true
        System.out.println(F("mississippi", "mis*is*p*.")); // false
    }
}
