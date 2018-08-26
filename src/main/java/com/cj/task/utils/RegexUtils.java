package com.cj.task.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by cj on 2018/8/26.
 */
public class RegexUtils {
    /**
     * 用户注册账号验证
     *
     * @param input
     * @return
     */
    public static boolean isAccount(CharSequence input) {
        return isMatch(RegexConstants.REGEX_ACCOUNT, input);
    }

    /**
     * 用户注册密码验证
     *
     * @param input
     * @return
     */
    public static boolean isPwd(CharSequence input) {
        return isMatch(RegexConstants.REGEX_PASSWORD, input);
    }

    /**
     * 正则表达式匹配
     *
     * @param regex
     * @param input
     * @return
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return (!StringUtils.isEmpty(input) && Pattern.matches(regex, input));
    }
}
