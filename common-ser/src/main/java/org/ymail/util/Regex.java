package org.ymail.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    /**
     * 判断正则是否匹配
     * @param str 要匹配的字符串
     * @param regex 规则
     * @return 是否ok
     */
    public static boolean validate(String str,String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
