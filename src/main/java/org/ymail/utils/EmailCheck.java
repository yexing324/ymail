package org.ymail.utils;

import org.ymail.entity.Email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCheck {

    public static void checkEmailAndInit(Email email){
        //
        checkFrom(email.getFrom());
        checkTo(email.getTo());
        //subject可以为空，不检测
        if(email.getSubject()==null){
            email.setSubject("无主题");
        }
    }
    public static void checkFrom(String str){
//        String emailAddr;
//        try {
//            //获得email的地址
//            emailAddr = getMesFromPattern(str, " <(.*?)>");
//        }catch (Exception e){
//            throw new RuntimeException("发件人email头部错误");
//        }
        if(validate(str, "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            throw new RuntimeException("发件人email地址错误");
        }
//        if(!str.trim().endsWith(">")){
//            throw new RuntimeException("发件人email头部错误");
//        }
//        return emailAddr;
    }
    public static String getNickName(String str){
        String[] split = str.trim().split(" ");
        return split[0];
    }

    public static void checkTo(String str){
        String emailAddr;
//        try {
//            //获得email的地址
//            emailAddr = getMesFromPattern(str, " <(.*?)>");
//        }catch (Exception e){
//            throw new RuntimeException("收件人email头部错误");
//        }
        if(validate(str, "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            throw new RuntimeException("收件人email地址错误");
        }
//        if(!str.trim().endsWith(">")){
//            throw new RuntimeException("收件人email头部错误");
//        }
    }
    //通过正则获得内容
    public static String getMesFromPattern(String str, String regex){
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    //判断与正则是否匹配
    public static boolean validate(String email,String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
}
