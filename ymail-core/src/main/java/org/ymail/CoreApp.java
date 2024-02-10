package org.ymail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.ymail.utils.ReceiverCore;

@SpringBootApplication
public class CoreApp {
    public static void main(String[] args) {
        //启动springboot后端以及服务监听
        ConfigurableApplicationContext context =
                SpringApplication.run(CoreApp.class, args);
        ReceiverCore receiverCore = context.getBean(ReceiverCore.class);
        receiverCore.startReceive();
    }
}