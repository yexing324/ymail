package org.ymail.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ymail.entity.SendEmail;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class Sender implements Runnable {
    public Sender(SendEmail email, BaseUtils baseUtils, String domain) {
        this.baseUtils = baseUtils;
        this.sendEmail = email;
        this.domain = domain;
    }

    private final BaseUtils baseUtils;
    private final SendEmail sendEmail;
    private Scanner in;
    private PrintWriter out;
    private Socket server;
    private String host;
    private String domain;
    private InputStream inputStream;
    private OutputStream outputStream;
    private DataInputStream reader;

    @SneakyThrows
    @Override
    public void run() {
        //处理发送逻辑
        System.out.println("即将发送" + sendEmail);
        sendEmail.setHtmlText("PGRpdiBzdHlsZT0iY29sb3I6cmVkIj7kvaDlpb08L2Rpdj4=");
        //首先获得host
        try {
            host = sendEmail.getTo().split("@")[1];
            //TODO:由于getHost会有一定的延迟
            //而mx地址并不常变，可以采用redis
            if (host.equals("163.com")) {
                host = "163mx01.mxmail.netease.com";
            } else
                host = baseUtils.getMailHost(host);
            server = new Socket(host, 25);
            inputStream = server.getInputStream();
            outputStream = server.getOutputStream();
            in = new Scanner(inputStream);
            out = new PrintWriter(outputStream);
            reader = new DataInputStream(inputStream);
            receive();

            send("HELO " + domain);
            receive();

            send("MAIL FROM:<" + sendEmail.getFrom() + ">");
            receive();

            send("RCPT TO:<" + sendEmail.getTo() + ">");
            receive();

            send("DATA");
            receive();
            send("h=From:To:Subject:Date;");
//            send("DKIM-Signature: v=1; a=rsa-sha256; c=relaxed/relaxed;");
            send(sendEmail.getSendFrom());
            send(sendEmail.getSendTo());
            send(sendEmail.getSubject());
            send("Content-Type: multipart/alternative;");
            send("boundary=\""+sendEmail.getEnd()+"\"");
            send("MIME-Version: 1.0");
            send(sendEmail.getMessageId());

            //
            //plain头部
            send("");
            send("--"+sendEmail.getEnd());
            send(sendEmail.getPlainType());
            send(sendEmail.getPlainBase64());
            //准备发送正文
            send("");
            send(sendEmail.getPlainText());
            send("");
            send("--"+sendEmail.getEnd());
            //暂时不发送html内容
            send(sendEmail.getHtmlType());
            send(sendEmail.getHtmlBase64());
            send("");
            send(sendEmail.getHtmlText());
            send("");
            send("--"+sendEmail.getEnd()+"--");
            send("");
            send(".");
//            send("");
            receive();
            send("QUIT");
            receive();
            //对数据库进行操作
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败");
            //TODO:发送失败逻辑
        } finally {
            //处理结束逻辑

            while (reader.available() > 0) {
                String line = in.nextLine();
//                log.warn("server:"+line);
//                System.out.println("server:" + line);
                Thread.sleep(2000);
            }

            System.out.println("服务结束");
//            server.close();
        }

    }

    private void send(String str) {
        System.out.println("Sender:" + str);
        out.print(str.replaceAll("\n", "\r\n") + "\r\n");
        out.flush();
    }

    private void receive() {
        try {
            try {
                //不延迟的话，可能无法及时收到消息
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (in.hasNextLine()) {
                String line = in.nextLine();
//                log.warn("server:"+line);
                System.out.println("server:" + line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
