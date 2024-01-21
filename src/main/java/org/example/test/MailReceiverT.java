package org.example.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 收信端服务器
 */
public class MailReceiverT {
    private ServerSocket server;
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader reader;
    private int count;
    private InputStream inputStream;
    protected OutputStream outputStream;

    Scanner in;

    public void receiveMail() {

        try {
            server = new ServerSocket(28);
            // 写日志文件
            log("服务器启动等待连接......");
            // 启动服务
            socket = server.accept();
            in = new Scanner(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            log("收到主机" + socket.getInetAddress() + socket.getInetAddress().getHostName() + "的连接请求！\n");
            send("220 smtp.slovety.top SMTP CSUSOFTMAIL (Postfix Rules!)" + "\r\n");
            send("250 OK");
            // 写日志文件
            getmsg();
            send("250 OK smtp.shexu.com" + "\r\n");
            getmsg();

            send("250 OK Sender" + "\r\n");
            getmsg();
            send("250 OK Receiver" + "\r\n");
            getmsg();
            send("354 Start mail input; end with <CRLF>.<CRLF>" + "\r\n");
            String mes = new String("");
            while (true) {
                String getmsg = getmsg();
                mes += getmsg;
                if (getmsg.contains("plain")) {
                    String charset = "";
                    charset = getmsg.contains("charset") ? getChatSet(getmsg):charset;
                    boolean base = false;
                    String line = getmsg();
                    while (!(line == null || line.equals(""))) {
                        charset = line.contains("charset") ? getChatSet(line):charset;
                        base = base || line.contains("base64");
                        line = getmsg();
                    }
                    System.out.println("编码是:"+charset);
                    //开始正文...
                    line = getmsg();
                    StringBuilder plainText = new StringBuilder();
                    while (!line.startsWith("--")) {
                        plainText.append(line);
                        line = getmsg();
                    }
                    if (base) {//进行了64编码
                        from64(String.valueOf(plainText), charset);
                    } else {
                        System.out.println(plainText + charset);
                    }
                    System.out.println("正文结束:" + plainText);
                }
                if (getmsg.equals(".")) {
                    break;
                }
            }
            System.out.println("结束了");
            send("250 OK Receiver" + "\r\n");
            getmsg();
            send("221 smtp.slovety.top Service closing transmission channel ！GoodBye...");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    public void receive(){
//        count++;
//        String str = null;
//        try {
//            if(!(str = reader.readLine()).trim().equals("")){
//                System.out.println(count + "in：" + str);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public String getmsg() {
        if (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println("remote:" + line);
            return line;
        }
        return null;
    }

    /**
     * 读取正文并保存到文件
     */
//    public void receiveData(){
//        char[] temp = new char[512];
//        String str = null;
//        int count = 0;
//        try {
//            while(!(str = reader.readLine()).trim().equals(".")){
//                count++;
//                System.out.println("收到客户端Date信息：" + str);
//                log("MailReceiver","Info","收到客户端信息：" + str.trim());
//                // 消除开头换行
//                if(count >= 4 && count <=11){
//
//                }else {
//
//                }
//
//            }
//            System.out.println("收到客户端Date信息：" + str);
//            log("MailReceiver","Info","收到客户端信息：" + str.trim());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    public void send(String str) {
        log("sender:" + str.trim() + "\r\n");
        pw.print(str);
        pw.flush();
    }

    public static void main(String[] args) {
        MailReceiverT mr = new MailReceiverT();
        while (true) {
            mr.receiveMail();
        }
    }

    public void log(String... s) {
        for (int i = 0; i < s.length; i++) {
            System.out.printf(s[i]);
        }
        System.out.println();
    }

    public void from64(String x, String code) {
        try {
//            String text = "Java 8 Base64 编码解码 - Java8新特性";
//            String base64encodedString = Base64.getUrlEncoder().encodeToString(text.getBytes("utf-8"));
//            System.out.println(base64encodedString);
            byte[] base64decodedBytes = Base64.getUrlDecoder().decode(x);
            System.out.println("内容是:" + new String(base64decodedBytes, code));
        } catch (UnsupportedEncodingException e) {
            System.out.println("异常：" + e.getMessage());
        }
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
        }else{
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