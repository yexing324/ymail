package org.ymail.utils;

import org.ymail.entity.ReceiveEmail;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 接收邮件者
 */
public class Receiver implements Runnable {
    private final Socket socket;
    private PrintWriter pw;

    private Scanner in;
    private final BaseUtils baseUtils;
    private ReceiveEmail receiveEmail;


    public Receiver(Socket socket, BaseUtils baseUtils) {
        this.socket = socket;
        this.baseUtils = baseUtils;
    }

    @Override
    public void run() {
        receiveEmail = new ReceiveEmail();
        try {
            in = new Scanner(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream());
            send("220 smtp.slovety.top SMTP CSUSOFTMAIL (Postfix Rules!)");
            // 写日志文件
            //获得正文
            while (true) {
                String msg = getMsg();
                //
                if (msg.startsWith("EHLO")) {
                    send("250 OK smtp.shexu.com");
                } else if (msg.contains("FROM")) {
                    send("250 OK Sender");
                } else if (msg.contains("TO")) {
                    send("250 OK Receiver");
                } else if (msg.startsWith("DATA")) {
                    send("354 Start mail input; end with <CRLF>.<CRLF>");
                } else if (msg.startsWith("From")) {
                    getFrom(msg);
                } else if (msg.startsWith("To")) {
                    getTo(msg);
                } else if (msg.startsWith("Subject")) {
                    getSubject(msg);
                }
                //处理正文
                else if (msg.contains("plain")) {
                    String charset = "";
                    charset = msg.contains("charset") ? getChatSet(msg) : charset;
                    boolean base = false;
                    String line = getMsg();
                    while (!(line == null || line.isEmpty())) {
                        charset = line.contains("charset") ? getChatSet(line) : charset;
                        base = base || line.contains("base64");
                        line = getMsg();
                    }
                    //开始正文...
                    line = getMsg();
                    StringBuilder plainText = new StringBuilder();
                    while (!line.startsWith("--")) {
                        plainText.append(line);
                        line = getMsg();
                    }
                    if (base) {//进行了64编码
                        String plain = baseUtils.getFrom64(String.valueOf(plainText), charset);
                        receiveEmail.setPlainText(plain);
                    } else {
                        receiveEmail.setPlainText(String.valueOf(plainText));
                    }
                } else if (msg.contains("html")) {
                    String charset = "";
                    charset = msg.contains("charset") ? getChatSet(msg) : charset;
                    boolean base = false;
                    String line = getMsg();
                    while (!(line == null || line.isEmpty())) {
                        charset = line.contains("charset") ? getChatSet(line) : charset;
                        base = base || line.contains("base64");
                        line = getMsg();
                    }
                    //开始正文...
                    line = getMsg();
                    StringBuilder htmlText = new StringBuilder();
                    while (!line.startsWith("--")) {
                        htmlText.append(line);
                        line = getMsg();
                    }
                    if (base) {//进行了64编码
                        String html = baseUtils.getFrom64(String.valueOf(htmlText), charset);
                        receiveEmail.setHtmlText(html);
                    } else {
                        receiveEmail.setHtmlText(String.valueOf(htmlText));
                    }
                } else if (msg.contains("Message-ID")) {
                    String addrP = " <(.*?)>";//获得邮件
                    Matcher matcher = Pattern.compile(addrP).matcher(msg);
                    if (matcher.find()) {
                        receiveEmail.setMessageId(matcher.group(1));
                    }
                } else if (msg.equals(".")) {
                    send("250 OK Receiver");
                } else if (msg.equals("QUIT")) {
                    send("221 smtp.slovety.top Service closing transmission channel ！GoodBye...");
                    break;
                } else {
                    send("250 OK Receiver");
                }

            }


        } catch (Exception e) {
            throw new RuntimeException("转化邮件"+e.getMessage());
        } finally {
            //线程睡眠2秒
            System.out.println("接收到的邮件是");
            System.out.println(receiveEmail.toString());
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("关闭socket出错");
                }
            }
        }

    }

    public String getMsg() {
        if (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println("remote:" + line);
            return line;
        }
        return null;
    }


    public void send(String str) {
        System.out.println("接收者:sender:" + str.trim() + "\r\n");
        pw.print(str + "\r\n");
        pw.flush();
    }

    public void getFrom(String str) {
        String code = "", name = "", addr = "";
        if (str.contains("=?")) {//采用了编码
            String codeP = "=?\\?(.*?)\\?B?";//获得编码
            String nameP = "\\?B?\\?(.*?)\\?=";//获得名称
            String addrP = " <(.*?)>";//获得邮件

            Matcher matcher = Pattern.compile(codeP).matcher(str);
            if (matcher.find()) {
                code = matcher.group(1);
            }
            matcher = Pattern.compile(nameP).matcher(str);

            if (matcher.find()) {
                String nameBase = matcher.group(1);
                name = baseUtils.getFrom64(nameBase, code);
            }
            matcher = Pattern.compile(addrP).matcher(str);
            if (matcher.find()) {
                addr = matcher.group(1);
            }
        } else {//没有采用编码，可以直接处理

            String addrP = " <(.*?)>";//获得邮件
            String nameP = "From: (.*?) <";//获得名称
            Matcher matcher = Pattern.compile(nameP).matcher(str);
            if(matcher.find()){
                name = matcher.group(1);
            }
            matcher = Pattern.compile(addrP).matcher(str);
            if(matcher.find()){
                addr = matcher.group(1);
            }
        }
        receiveEmail.setFrom(addr);
        receiveEmail.setNickname(name);
    }

    public void getTo(String str) {
        String code = "", name = "", addr = "";
        if (str.contains("=?")) {//采用了编码
            String codeP = "=?\\?(.*?)\\?B?";//获得编码
            String nameP = "\\?B?\\?(.*?)\\?=";//获得名称
            String addrP = " <(.*?)>";//获得邮件
            Matcher matcher = Pattern.compile(codeP).matcher(str);
            if(matcher.find()){
                code = matcher.group(1);
            }
            matcher = Pattern.compile(nameP).matcher(str);
            if(matcher.find()){
                String nameBase = matcher.group(1);
                name = baseUtils.getFrom64(nameBase, code);
            }
            matcher = Pattern.compile(addrP).matcher(str);
            if(matcher.find()){
                addr = matcher.group(1);
            }
            //TODO:接收者的name暂时没有处理
        } else if (str.contains("<")) {//没有采用编码，但是有<>

            String addrP = " <(.*?)>";//获得邮件
            String nameP = "To: (.*?) <";//获得名称
            Matcher matcher = Pattern.compile(nameP).matcher(str);
            if(matcher.find()){
                name = matcher.group(1);
            }
            matcher = Pattern.compile(addrP).matcher(str);
            if(matcher.find()){
                addr = matcher.group(1);
            }

        } else {//什么都没有的
            String[] split = str.split(" ");
            addr = split[1];
        }
        receiveEmail.setTo(addr);
    }

    public void getSubject(String str) {
        String code = "", theme = "";
        if (str.contains("=?")) {//采用了编码
            String codeP = "=?\\?(.*?)\\?B?";//获得编码
            String themeP = "\\?B?\\?(.*)";//获得名称
            Matcher matcher = Pattern.compile(codeP).matcher(str);
            if(matcher.find()){
                code = matcher.group(1);
            }
            matcher = Pattern.compile(themeP).matcher(str);
            if(matcher.find()){
                String themeBase = matcher.group(1);
                theme = baseUtils.getFrom64(themeBase, code);
            }
        } else {//没有采用编码，可以直接处理

            String themeP = ": (.*)";//获得邮件
            Matcher matcher = Pattern.compile(themeP).matcher(str);
            if(matcher.find()){
                theme = matcher.group(1);
            }
        }
        receiveEmail.setSubject(theme);
    }

    public String getChatSet(String input) {
        String charset = "";
        if (input.contains("\"")) {
            String pattern = "charset=\"([^\"]+)\"";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(input);
            if (matcher.find()) {
                charset = matcher.group(1);
                return charset;
            }
        } else {
            String pattern = "charset=([a-zA-Z0-9-]+)";

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(input);

            if (matcher.find()) {
                charset = matcher.group(1);
                return charset;
            }
        }
        return "UTF-8";
    }

}
