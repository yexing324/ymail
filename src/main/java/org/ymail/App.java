package org.ymail;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.ymail.utils.ReceiverCore;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        //启动springboot后端以及服务监听
        ConfigurableApplicationContext context =
                SpringApplication.run(App.class, args);
        ReceiverCore receiverCore = context.getBean(ReceiverCore.class);
        receiverCore.startReceive();
    }
}