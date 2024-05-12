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

    public static boolean isEmail(String mail) {
        return validate(mail,"^[A-Za-z0-9]+([-._][A-Za-z0-9]+)*@[A-Za-z0-9]+(-[A-Za-z0-9]+)*(.[A-Za-z]{2,6}|[A-Za-z]{2,4}.[A-Za-z]{2,3})$");
    }
}
