package org.ymail.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeContain {
    public static String uploadAddress="http://localhost/ymail/upload/getImg?id=";
    public static List<String>nickNameList= Arrays.asList(
            "爱意随风起"
            ,"风华浪子"
            ,"风止意难平"
            ,"黛玉恋花"
            ,"依恋"
            ,"朗朗"
            ,"ViVi"
            ,"小宅"
            ,"康康"
            ,"小猫咪"
            ,"柳如烟"
            ,"秋水"
            ,"洛神"
            );

    public static List<String>systemGroup=Arrays.asList(
            "收件箱"
            ,"草稿箱"
            ,"垃圾箱"
            ,"已发送"
            ,"已删除"
            );
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
