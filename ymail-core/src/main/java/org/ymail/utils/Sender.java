package org.ymail.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ymail.entity.UploadFile;
import org.ymail.entity.SendEmail;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static org.ymail.enums.EmailType.*;

/**
 * 发送邮件的实际执行者
 */
@Slf4j
public class Sender implements Runnable {
    public Sender(SendEmail email, BaseUtils baseUtils, String domain) {
        this.baseUtils = baseUtils;
        this.sendEmail = email;
        this.domain = domain;
    }

    private final BaseUtils baseUtils;
    private final SendEmail sendEmail;
    private  Scanner in;
    private PrintWriter out;
    private final String domain;
    private DataInputStream reader;
    private Socket server;

    @SneakyThrows
    @Override
    public void run() {
        //处理发送逻辑
        System.out.println("即将发送" + sendEmail);

        try {
            String host = sendEmail.getTo().split("@")[1];
            //TODO:由于getHost会有一定的延迟
            //而mx地址并不常变，可以采用redis
            if (host.equals("163.com")) {
                host = "163mx01.mxmail.netease.com";
            } else
                host = baseUtils.getMailHost(host);
            server = new Socket(host, 25);
            InputStream inputStream = server.getInputStream();
            OutputStream outputStream = server.getOutputStream();
            in = new Scanner(inputStream);
            out = new PrintWriter(outputStream);
            reader = new DataInputStream(inputStream);
            receive();

            sendTitle();

            send("DATA");
            receive();

            sendFromAndToS();

            sendType();


            //开始正文
            if(sendEmail.getType().equals(mixed.getValue())){
                //复杂型
                send("        boundary=\"" + sendEmail.getMixBoundary() + "\"");
                send("MIME-Version: 1.0");
                send(sendEmail.getMessageId());

                send("");
                send("--" + sendEmail.getMixBoundary());

                sendRelated();

                sendAttachments();

                send("--" + sendEmail.getMixBoundary() + "--");
            }else{
                sendRelated();
            }

            send("");
            send(".");
            receive();
            send("QUIT");
            receive();
            //对数据库进行操作
        } catch (Exception e) {
            throw new RuntimeException("邮件发送失败");
            //TODO:发送失败逻辑
        } finally {
            //处理结束逻辑
            while (reader.available() > 0) {
                String line = in.nextLine();
                Thread.sleep(2000);
            }
            server.close();
            System.out.println("服务结束");
        }

    }
    private void sendAttachments(){
        for (UploadFile file : sendEmail.getAttachments()) {
            send("--" + sendEmail.getMixBoundary());
            send("Content-Type: image/jpeg;\n        name="+file.getName());
            send("Content-Transfer-Encoding: base64");
            send("Content-Disposition: attachment;filename="+file.getName());
            send("");
            send(baseUtils.getFileToBase64(file.getName(),"file"));
        }
    }
    private void sendRelated(){

        if (sendEmail.getImageList() != null && !sendEmail.getImageList().isEmpty()) {
            //有内嵌
            if(!sendEmail.getType().equals(related.getValue())){
                send("Content-Type: multipart/related;");
            }
            send("        boundary=\"" + sendEmail.getRelateBoundary() + "\"");
            send("");
            send("This is a multi-part message in MIME format.");
            send("");
            send("--" + sendEmail.getRelateBoundary());
            sendAlternative();

            sendImage();
            send("--" + sendEmail.getRelateBoundary() + "--");

        }else{
            sendAlternative();
        }
    }
    private void sendImage(){
        for (UploadFile uploadFile : sendEmail.getImageList()) {
            send("--" + sendEmail.getRelateBoundary());
            send("Content-Type: image/jpeg;\n        name=123.jpg");
            send("Content-Transfer-Encoding: base64");
            send("Content-Disposition: inline;filename=123.jpg");
            send("Content-ID: <" + uploadFile.getFileID() + ">");
            send("");
            send(baseUtils.getFileToBase64(uploadFile.getName(),"img"));
        }
    }
    private void sendAlternative(){
        send("Content-Type: multipart/alternative;");
        send("        boundary=\"" + sendEmail.getAlternativeBoundary() + "\"");
        //plain头部
        send("");
        send("--" + sendEmail.getAlternativeBoundary());

        send(sendEmail.getPlainType());
        send(sendEmail.getPlainBase64());
        //准备发送正文
        send("");
        send(sendEmail.getPlainText());
        send("");
        send("--" + sendEmail.getAlternativeBoundary());
        //暂时不发送html内容
        send(sendEmail.getHtmlType());
        send(sendEmail.getHtmlBase64());
        send("");
        send(sendEmail.getHtmlText());
        send("");
        send("--" + sendEmail.getAlternativeBoundary() + "--");
        send("");
    }

    private void sendTitle() {
        send("HELO " + domain);
        receive();

        send("MAIL FROM:<" + sendEmail.getFrom() + ">");
        receive();

        send("RCPT TO:<" + sendEmail.getTo() + ">");
        receive();
    }

    private void sendFromAndToS() {

        send("h=From:To:Subject:Date;");
//            send("DKIM-Signature: v=1; a=rsa-sha256; c=relaxed/relaxed;");
        send(sendEmail.getSendFrom());
        send(sendEmail.getSendTo());
        send(sendEmail.getSubject());
    }

    /**
     * 设置邮件发送时的type
     */

    private void sendType() {
        if (sendEmail.getAttachments() != null && !sendEmail.getAttachments().isEmpty()) {
            send("Content-Type: multipart/mixed;");
            sendEmail.setType(mixed.getValue());
            return;
        }
        if (sendEmail.getImageList() != null && !sendEmail.getImageList().isEmpty()) {
            send("Content-Type: multipart/related;");
            sendEmail.setType(related.getValue());
        } else {
            send("Content-Type: multipart/alternative;");
            sendEmail.setType(alternative.getValue());
        }
    }

    private void send(String str) {
        System.out.println(str);
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
                System.out.println("server:" + line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
