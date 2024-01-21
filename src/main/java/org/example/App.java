package org.example;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class App {
    static class Email {
        String subject;

        @Override
        public String toString() {
            return "Email{" +
                    "subject='" + subject + '\'' +
                    '}';
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }

    public static void main(String[] args) {
//        Email  e=new Email();
//        e.setSubject("hello");
////        JSON.toJSONString();
//        String json = JSON.toJSONString("sub:asd");
//        System.out.println(json);
//        Email parse = JSON.parseObject(json, Email.class);
////        Email javaObject = JSON.toJavaObject(, Email.class);
//        System.out.println(parse);
//        from64("xOO6w7Ch","gbk");


//        String input = "Content-Type: text/plain; charset=\"GBK\"";
//
//        // 根据分号和等号进行分割
//        String[] parts = input.split("[;=]");
//
//        // 遍历分割的结果，查找字符集编码
//        for (int i = 0; i < parts.length; i++) {
//            if (parts[i].trim().equals("charset")) {
//                String charset = parts[i + 1].trim();
//                System.out.println(charset);
//                break;
//            }
//        }

        System.out.println(getChatSet(" charset=gb18030"));



    }

    public static String getChatSet(String input) {
        if (input.contains("\"")) {
            String pattern = "charset=\"([^\"]+)\"";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(input);
            if (matcher.find()) {
                String charset = matcher.group(1);
                return charset;
            }
        }else{
            String pattern = "charset=([a-zA-Z0-9-]+)";

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(input);

            if (matcher.find()) {
                String charset = matcher.group(1);
                return charset;
            }
        }
        return "UTF-8";
    }

    public static void from64(String x, String code) {
        try {
//            String text = "Java 8 Base64 编码解码 - Java8新特性";
//            String base64encodedString = Base64.getUrlEncoder().encodeToString(text.getBytes("utf-8"));
//            System.out.println(base64encodedString);
            byte[] base64decodedBytes = Base64.getUrlDecoder().decode(x);
            System.out.println("内容是" + new String(base64decodedBytes, code));
        } catch (UnsupportedEncodingException e) {
            System.out.println("异常：" + e.getMessage());
        }
    }
}
