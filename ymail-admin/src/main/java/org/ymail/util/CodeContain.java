package org.ymail.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeContain {
    public static Map<String,String> codeMap=new ConcurrentHashMap<>();
    public static void addCode(String phone,String code)
    {
        codeMap.put(phone,code);
    }
    public static String getCode(String phone)
    {
        return codeMap.get(phone);
    }
    public static void removeCode(String phone)
    {
        codeMap.remove(phone);
    }
}
