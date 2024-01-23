package org.ymail.utils;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.ymail.entity.SendEmail;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable {
    public Sender(SendEmail email, BaseUtils baseUtils,String domain) {
        this.baseUtils = baseUtils;
        this.sendEmail = email;
        this.domain=domain;
    }

    private final BaseUtils baseUtils;
    private final SendEmail sendEmail;
    private Scanner in;
    private PrintWriter out;
    private Socket server;
    private String host;
    private String domain;

    @Override
    public void run() {
        //处理发送逻辑
        System.out.println("即将发送" + sendEmail);
        //首先获得host
        try{
            host = sendEmail.getFrom().split("[@>]")[1];
            //TODO:由于getHost会有一定的延迟
            //而mx地址并不常变，可以采用redis
            host = baseUtils.getMailHost(host);
            server=new Socket(host,25);
            in=new Scanner(server.getInputStream());
            out=new PrintWriter(server.getOutputStream());


        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败");
            //TODO:发送失败逻辑
        }

    }
}
