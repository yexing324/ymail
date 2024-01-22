package org.example;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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

    public static void main(String[] args) throws UnsupportedEncodingException {
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

//        from64("PG1ldGEgaHR0cC1lcXVpdj0iQ29udGVudC1UeXBlIiBjb250ZW50PSJ0ZXh0L2h0bWw7IGNoYXJzZXQ9R0IxODAzMCI+PGRpdj5oZWxsbyx0aGlzIGlzIGEgdGVzdCBlbWFpbDwvZGl2PjxkaXY+xOO7ubrDwvA8L2Rpdj48ZGl2PjxociBhbGlnbj0ibGVmdCIgc3R5bGU9Im1hcmdpbjogMCAwIDEwcHggMDtib3JkZXI6IDA7Ym9yZGVyLWJvdHRvbToxcHggc29saWQgI0U0RTVFNjtoZWlnaHQ6MDtsaW5lLWhlaWdodDowO2ZvbnQtc2l6ZTowO3BhZGRpbmc6IDIwcHggMCAwIDA7d2lkdGg6IDUwcHg7Ij48ZGl2IHN0eWxlPSJmb250LXNpemU6MTRweDtmb250LWZhbWlseTpWZXJkYW5hO2NvbG9yOiMwMDA7Ij48YSBjbGFzcz0ieG1fd3JpdGVfY2FyZCIgaWQ9ImluX2FsaWFzIiBzdHlsZT0id2hpdGUtc3BhY2U6IG5vcm1hbDsgZGlzcGxheTogaW5saW5lLWJsb2NrOyB0ZXh0LWRlY29yYXRpb246IG5vbmUgIWltcG9ydGFudDtmb250LWZhbWlseTogLWFwcGxlLXN5c3RlbSxCbGlua01hY1N5c3RlbUZvbnQsUGluZ0ZhbmcgU0MsTWljcm9zb2Z0IFlhSGVpOyIgaHJlZj0iaHR0cHM6Ly93eC5tYWlsLnFxLmNvbS9ob21lL2luZGV4P3Q9cmVhZG1haWxfYnVzaW5lc3NjYXJkX21pZHBhZ2UmYW1wO25vY2hlY2s9dHJ1ZSZhbXA7bmFtZT0lRTUlQjAlOEYlRTclODYlOEElRTclOEMlQUImYW1wO2ljb249aHR0cCUzQSUyRiUyRnRoaXJkcXEucWxvZ28uY24lMkZnJTNGYiUzRHNkayUyNmslM0RHYXJmT2lhVWljdkwyVzMwNkNmMGlhVG1BJTI2cyUzRDEwMCUyNnQlM0QxNjM5Mjk3NDg3JTNGcmFuZCUzRDE2NDczNDQyMzYmYW1wO21haWw9MjQyMDIzMzAyNSU0MHFxLmNvbSZhbXA7Y29kZT0iIHRhcmdldD0iX2JsYW5rIj48dGFibGUgc3R5bGU9IndoaXRlLXNwYWNlOiBub3JtYWw7dGFibGUtbGF5b3V0OiBmaXhlZDsgcGFkZGluZy1yaWdodDogMjBweDsiIGNvbnRlbnRlZGl0YWJsZT0iZmFsc2UiIGNlbGxwYWRkaW5nPSIwIiBjZWxsc3BhY2luZz0iMCI+PHRib2R5Pjx0ciB2YWxpZ249InRvcCI+PHRkIHN0eWxlPSJ3aWR0aDogNDBweDttaW4td2lkdGg6IDQwcHg7IHBhZGRpbmctdG9wOjEwcHgiPjxkaXYgc3R5bGU9IndpZHRoOiAzOHB4OyBoZWlnaHQ6IDM4cHg7IGJvcmRlcjogMXB4ICNGRkYgc29saWQ7IGJvcmRlci1yYWRpdXM6NTAlOyBtYXJnaW46IDA7dmVydGljYWwtYWxpZ246IHRvcDtib3gtc2hhZG93OiAwIDAgMTBweCAwIHJnYmEoMTI3LDE1MiwxNzgsMC4xNCk7Ij48aW1nIHNyYz0iaHR0cDovL3RoaXJkcXEucWxvZ28uY24vZz9iPXNkayZhbXA7az1HYXJmT2lhVWljdkwyVzMwNkNmMGlhVG1BJmFtcDtzPTEwMCZhbXA7dD0xNjM5Mjk3NDg3P3JhbmQ9MTY0NzM0NDIzNiIgc3R5bGU9IndpZHRoOjEwMCU7Ym9yZGVyLXJhZGl1czo1MCU7cG9pbnRlci1ldmVudHM6IG5vbmU7Ij48L2Rpdj48L3RkPjx0ZCBzdHlsZT0icGFkZGluZzogMTBweCAwIDhweCAxMHB4OyI+PGRpdiBjbGFzcz0iYnVzaW5lc3NDYXJkX25hbWUiIHN0eWxlPSJmb250LXNpemU6IDE0cHg7Y29sb3I6ICMzMzMxMkU7bGluZS1oZWlnaHQ6IDIwcHg7IHBhZGRpbmctYm90dG9tOiAycHg7IG1hcmdpbjowO2ZvbnQtd2VpZ2h0OiA1MDA7Ij7QodDcw6g8L2Rpdj48ZGl2IGNsYXNzPSJidXNpbmVzc0NhcmRfbWFpbCIgc3R5bGU9ImZvbnQtc2l6ZTogMTJweDtjb2xvcjogIzk5OTg5NjtsaW5lLWhlaWdodDogMThweDsgbWFyZ2luOjA7Ij4yNDIwMjMzMDI1QHFxLmNvbTwvZGl2PjwvdGQ+PC90cj48L3Rib2R5PjwvdGFibGU+PC9hPjwvZGl2PjwvZGl2PjxkaXY+Jm5ic3A7PC9kaXY+"
//                        .replace("\r\n", ""),
//                "gb18030");
//        final Base64.Decoder decoder = Base64.getDecoder();
//        String base64Str = "PG1ldGEgaHR0cC1lcXVpdj0iQ29udGVudC1UeXBlIiBjb250ZW50PSJ0ZXh0L2h0bWw7IGNoYXJzZXQ9R0IxODAzMCI+PGRpdj5oZWxsbyx0aGlzIGlzIGEgdGVzdCBlbWFpbDwvZGl2PjxkaXY+xOO7ubrDwvA8L2Rpdj48ZGl2PjxociBhbGlnbj0ibGVmdCIgc3R5bGU9Im1hcmdpbjogMCAwIDEwcHggMDtib3JkZXI6IDA7Ym9yZGVyLWJvdHRvbToxcHggc29saWQgI0U0RTVFNjtoZWlnaHQ6MDtsaW5lLWhlaWdodDowO2ZvbnQtc2l6ZTowO3BhZGRpbmc6IDIwcHggMCAwIDA7d2lkdGg6IDUwcHg7Ij48ZGl2IHN0eWxlPSJmb250LXNpemU6MTRweDtmb250LWZhbWlseTpWZXJkYW5hO2NvbG9yOiMwMDA7Ij48YSBjbGFzcz0ieG1fd3JpdGVfY2FyZCIgaWQ9ImluX2FsaWFzIiBzdHlsZT0id2hpdGUtc3BhY2U6IG5vcm1hbDsgZGlzcGxheTogaW5saW5lLWJsb2NrOyB0ZXh0LWRlY29yYXRpb246IG5vbmUgIWltcG9ydGFudDtmb250LWZhbWlseTogLWFwcGxlLXN5c3RlbSxCbGlua01hY1N5c3RlbUZvbnQsUGluZ0ZhbmcgU0MsTWljcm9zb2Z0IFlhSGVpOyIgaHJlZj0iaHR0cHM6Ly93eC5tYWlsLnFxLmNvbS9ob21lL2luZGV4P3Q9cmVhZG1haWxfYnVzaW5lc3NjYXJkX21pZHBhZ2UmYW1wO25vY2hlY2s9dHJ1ZSZhbXA7bmFtZT0lRTUlQjAlOEYlRTclODYlOEElRTclOEMlQUImYW1wO2ljb249aHR0cCUzQSUyRiUyRnRoaXJkcXEucWxvZ28uY24lMkZnJTNGYiUzRHNkayUyNmslM0RHYXJmT2lhVWljdkwyVzMwNkNmMGlhVG1BJTI2cyUzRDEwMCUyNnQlM0QxNjM5Mjk3NDg3JTNGcmFuZCUzRDE2NDczNDQyMzYmYW1wO21haWw9MjQyMDIzMzAyNSU0MHFxLmNvbSZhbXA7Y29kZT0iIHRhcmdldD0iX2JsYW5rIj48dGFibGUgc3R5bGU9IndoaXRlLXNwYWNlOiBub3JtYWw7dGFibGUtbGF5b3V0OiBmaXhlZDsgcGFkZGluZy1yaWdodDogMjBweDsiIGNvbnRlbnRlZGl0YWJsZT0iZmFsc2UiIGNlbGxwYWRkaW5nPSIwIiBjZWxsc3BhY2luZz0iMCI+PHRib2R5Pjx0ciB2YWxpZ249InRvcCI+PHRkIHN0eWxlPSJ3aWR0aDogNDBweDttaW4td2lkdGg6IDQwcHg7IHBhZGRpbmctdG9wOjEwcHgiPjxkaXYgc3R5bGU9IndpZHRoOiAzOHB4OyBoZWlnaHQ6IDM4cHg7IGJvcmRlcjogMXB4ICNGRkYgc29saWQ7IGJvcmRlci1yYWRpdXM6NTAlOyBtYXJnaW46IDA7dmVydGljYWwtYWxpZ246IHRvcDtib3gtc2hhZG93OiAwIDAgMTBweCAwIHJnYmEoMTI3LDE1MiwxNzgsMC4xNCk7Ij48aW1nIHNyYz0iaHR0cDovL3RoaXJkcXEucWxvZ28uY24vZz9iPXNkayZhbXA7az1HYXJmT2lhVWljdkwyVzMwNkNmMGlhVG1BJmFtcDtzPTEwMCZhbXA7dD0xNjM5Mjk3NDg3P3JhbmQ9MTY0NzM0NDIzNiIgc3R5bGU9IndpZHRoOjEwMCU7Ym9yZGVyLXJhZGl1czo1MCU7cG9pbnRlci1ldmVudHM6IG5vbmU7Ij48L2Rpdj48L3RkPjx0ZCBzdHlsZT0icGFkZGluZzogMTBweCAwIDhweCAxMHB4OyI+PGRpdiBjbGFzcz0iYnVzaW5lc3NDYXJkX25hbWUiIHN0eWxlPSJmb250LXNpemU6IDE0cHg7Y29sb3I6ICMzMzMxMkU7bGluZS1oZWlnaHQ6IDIwcHg7IHBhZGRpbmctYm90dG9tOiAycHg7IG1hcmdpbjowO2ZvbnQtd2VpZ2h0OiA1MDA7Ij7QodDcw6g8L2Rpdj48ZGl2IGNsYXNzPSJidXNpbmVzc0NhcmRfbWFpbCIgc3R5bGU9ImZvbnQtc2l6ZTogMTJweDtjb2xvcjogIzk5OTg5NjtsaW5lLWhlaWdodDogMThweDsgbWFyZ2luOjA7Ij4yNDIwMjMzMDI1QHFxLmNvbTwvZGl2PjwvdGQ+PC90cj48L3Rib2R5PjwvdGFibGU+PC9hPjwvZGl2PjwvZGl2PjxkaXY+Jm5ic3A7PC9kaXY+";
//        String text = new String(decoder.decode(base64Str.replaceAll("\n","")), "UTF-8");
//        System.out.println(text);
//        String input="Subject: =?GBK?B?xOO6ww==?=";
////        String pattern = "=?\\?(.*?)\\?B?";//获得编码
////        String pattern = " <(.*?)>";//获得邮件
//        String pattern = ": (.*)";//获得名称
//
//        Pattern regex = Pattern.compile(pattern);
//        Matcher matcher = regex.matcher(input);
//        if (matcher.find()) {
//            String charset = matcher.group(1);
//            System.out.println(charset);
//        }
        getSubject("Subject: =?gb18030?B?1vfM4g==?=");

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

//    public static void from64(String x, String code) {
//        try {
////            String text = "Java 8 Base64 编码解码 - Java8新特性";
////            String base64encodedString = Base64.getUrlEncoder().encodeToString(text.getBytes("utf-8"));
////            System.out.println(base64encodedString);
//            byte[] base64decodedBytes = Base64.getUrlDecoder().decode(x);
//            System.out.println("内容是" + new String(base64decodedBytes, code));
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("异常：" + e.getMessage());
//        }
//    }

    public static void getTo(String str) {
        String code = "", name = "", addr = "";
        if (str.contains("=?")) {//采用了编码
            String codeP = "=?\\?(.*?)\\?B?";//获得编码
            String nameP = "\\?B?\\?(.*?)\\?=";//获得名称
            String addrP = " <(.*?)>";//获得邮件
            Matcher matcher = Pattern.compile(codeP).matcher(str);
            matcher.find();
            code = matcher.group(1);
            matcher=Pattern.compile(nameP).matcher(str);
            matcher.find();
            name = matcher.group(1);
            matcher=Pattern.compile(addrP).matcher(str);
            matcher.find();
            addr = matcher.group(1);
            System.out.println("from的解析结果:"+code+name+addr);
        } else {//没有采用编码，可以直接处理

            String addrP = " <(.*?)>";//获得邮件
            String nameP = "To: (.*?) <";//获得名称
            Matcher matcher=Pattern.compile(nameP).matcher(str);
            matcher.find();
            name = matcher.group(1);
            matcher=Pattern.compile(addrP).matcher(str);
            matcher.find();
            addr = matcher.group(1);
            System.out.println("from的解析结果:"+code+name+addr);
        }
    }

    public static void getSubject(String str) {
        String code = "",  theme = "";
        if (str.contains("=?")) {//采用了编码
            String codeP = "=?\\?(.*?)\\?B?";//获得编码
            String themeP = "\\?B?\\?(.*)";//获得名称

            Matcher matcher = Pattern.compile(codeP).matcher(str);
            matcher.find();
            code = matcher.group(1);
            matcher=Pattern.compile(themeP).matcher(str);
            matcher.find();
            String themeBase = matcher.group(1);

            System.out.println("from的解析结果:"+code+from64(themeBase,code));
        } else {//没有采用编码，可以直接处理

            String themeP = ": (.*)";//获得邮件
            Matcher matcher=Pattern.compile(themeP).matcher(str);
            matcher.find();
            theme = matcher.group(1);
            System.out.println("from的解析结果:"+theme);
        }
    }

    public static String from64(String x, String code) {
        Base64.Decoder decoder = Base64.getMimeDecoder();
        try {

//            x = x.replaceAll(" ","+");
//            x=x.trim();
            String text = new String(decoder.decode(x.replaceAll("\n", "")), code);
            System.out.println("内容是:" + text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "出现了异常";
        }
//        try {
////            String text = "Java 8 Base64 编码解码 - Java8新特性";
////            String base64encodedString = Base64.getUrlEncoder().encodeToString(text.getBytes("utf-8"));
////            System.out.println(base64encodedString);
//            byte[] base64decodedBytes = Base64.getUrlDecoder().decode(x);
//            System.out.println("内容是:" + new String(base64decodedBytes, code));
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("异常：" + e.getMessage());
//        }
    }
}
