package org.ymail.util;

import okhttp3.Call;
import okhttp3.Response;

import java.util.Random;

public class SendCode {
    public static Http http = new Http();//网络对象
    public static void sendCode(String phone) {
        try {
            //发送号码
            Random random = new Random();
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                code.append(random.nextInt(10));
            }
            //保存code信息
            CodeContain.addCode(phone, String.valueOf(code));
            String s = "http://106.ihuyi.com/webservice/sms.php?method=Submit&account=C81228725&password=322d5abd65e775bb8996b7cb41c63de9&mobile=";
            s = s + phone + "&content=您的验证码是：";
            s = s + code + " 。请不要把验证码泄露给其他人。";
            System.out.println("验证码是："+code);
//            Call call = http.getCode(s);//发送给服务器
//            Response execute = call.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
