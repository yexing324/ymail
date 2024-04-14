//package org.ymail.test;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Scanner;
//
///**
// * 收信端服务器
// */
//public class MailReceiver {
//    private ServerSocket server;
//    private Socket socket;
//    private PrintWriter pw;
//    private BufferedReader reader;
//    private int count;
//    private InputStream inputStream;
//    protected OutputStream outputStream;
//
//    Scanner in;
//
//    public void receiveMail(){
//
//        try {
//            server = new ServerSocket(28);
//            // 写日志文件
//            log("服务器启动等待连接......");
//            // 启动服务
//            socket = server.accept();
//            in=new Scanner(socket.getInputStream());
//            pw = new PrintWriter(socket.getOutputStream());
//            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            log("收到主机" + socket.getInetAddress() + socket.getInetAddress().getHostName() +"的连接请求！\n");
//            send("220 smtp.shexu.com SMTP CSUSOFTMAIL (Postfix Rules!)" + "\t\n");
//            // 写日志文件
//
//            while (true){
//                String msg = getmsg();
//                if(msg.startsWith("HELO")){
//                    send("OK");
//                    Thread.sleep(1000);
//                    send("OK");
//                }else if(msg.startsWith("MAIL")){
//                    log("发送者是"+msg);
//                    send("Mail OK");
//                }else if(msg.startsWith("RCPT")){
//                    log("接收者是"+msg);
//                    send("Mail OK");
//                }else if("DATA".equals(msg)){
//                    log("收到data");
//                    send("354 End data with <CR><LF>.<CR><LF>");
//                }else if("QUIT".equals(msg)){
//                    log("收到quit");
//                    send("221 smtp.shexu.com Service closing transmission channel ！GoodBye...");
//                    break;
//                }else{
//                    log("收到其他命令"+msg);
//                    send("250 OK" + "\t\n");
//                }
//            }
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        } finally{
//            if(server != null){
//                try {
//                    server.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(socket != null){
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
////    public void receive(){
////        count++;
////        String str = null;
////        try {
////            if(!(str = reader.readLine()).trim().equals("")){
////                System.out.println(count + "in：" + str);
////            }
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//    public String getmsg(){
//        if (in.hasNextLine()) {
//            String line = in.nextLine();
//            System.out.println("remote:" + line);
//            return line;
//        }
//        return null;
//    }
//
//    /**
//     * 读取正文并保存到文件
//     */
////    public void receiveData(){
////        char[] temp = new char[512];
////        String str = null;
////        int count = 0;
////        try {
////            while(!(str = reader.readLine()).trim().equals(".")){
////                count++;
////                System.out.println("收到客户端Date信息：" + str);
////                log("MailReceiver","Info","收到客户端信息：" + str.trim());
////                // 消除开头换行
////                if(count >= 4 && count <=11){
////
////                }else {
////
////                }
////
////            }
////            System.out.println("收到客户端Date信息：" + str);
////            log("MailReceiver","Info","收到客户端信息：" + str.trim());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////    }
//
//    public void send(String str){
//        log("sender:" + str.trim());
//        pw.print(str);
//        pw.flush();
//    }
//
//    public static void main(String[] args) {
//        MailReceiver mr = new MailReceiver();
//        while(true){
//            mr.receiveMail();
//        }
//    }
//    public void log(String... s){
//        for (int i=0;i<s.length;i++){
//            System.out.printf(s[i]);
//        }
//        System.out.println();
//    }
//
//}


//package org.ymail.test;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Base64;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 收信端服务器
// */
//public class Receive {
//    private ServerSocket server;
//    private Socket socket;
//    private PrintWriter pw;
//    private BufferedReader reader;
//    private int count;
//    private InputStream inputStream;
//    protected OutputStream outputStream;
//
//    Scanner in;
//
//    public void receiveMail() {
//
//        try {
//            server = new ServerSocket(25);
//            socket = server.accept();
//            System.out.println("服务端启动");
//            in = new Scanner(socket.getInputStream());
//            pw = new PrintWriter(socket.getOutputStream());
//
//            // 写日志文件
//            log("收到主机" + socket.getInetAddress() + socket.getInetAddress().getHostName() + "的连接请求！\n");
//            send("220 smtp.slovety.top SMTP CSUSOFTMAIL (Postfix Rules!)");
//
//            while (true) {
//                String msg = getmsg();
//                if (msg.startsWith("EHLO")) {
//                    send("250 OK smtp.shexu.com");
//                } else if (msg.contains("FROM")) {
//                    send("250 OK Sender");
//                } else if (msg.contains("TO")) {
//                    send("250 OK Receiver");
//                } else if (msg.startsWith("DATA")) {
//                    send("354 Start mail input; end with <CRLF>.<CRLF>");
//                } else if (msg.startsWith("From")) {
//                    getFrom(msg);
//                } else if (msg.startsWith("To")) {
//                    getTo(msg);
//                } else if (msg.startsWith("Subject")) {
//                    getSubject(msg);
//                }
//                //
//                else if (msg.contains("plain")) {
//                    String charset = "";
//                    charset = msg.contains("charset") ? getChatSet(msg) : charset;
//                    boolean base = false;
//                    String line = getmsg();
//                    while (!(line == null || line.equals(""))) {
//                        charset = line.contains("charset") ? getChatSet(line) : charset;
//                        base = base || line.contains("base64");
//                        line = getmsg();
//                    }
//                    System.out.println("plain编码是:" + charset);
//                    //开始正文...
//                    line = getmsg();
//                    StringBuilder plainText = new StringBuilder();
//                    while (!line.startsWith("--")) {
//                        plainText.append(line);
//                        line = getmsg();
//                    }
//                    if (base) {//进行了64编码
//                        from64(String.valueOf(plainText), charset);
//                    } else {
//                        System.out.println(plainText + charset);
//                    }
//                    System.out.println("正文结束:" + plainText);
//                } else if (msg.contains("html")) {
//                    String charset = "";
//                    charset = msg.contains("charset") ? getChatSet(msg) : charset;
//                    boolean base = false;
//                    String line = getmsg();
//                    while (!(line == null || line.equals(""))) {
//                        charset = line.contains("charset") ? getChatSet(line) : charset;
//                        base = base || line.contains("base64");
//                        line = getmsg();
//                    }
//                    System.out.println("html编码是:" + charset);
//                    //开始正文...
//                    line = getmsg();
//                    StringBuilder plainText = new StringBuilder();
//                    while (!line.startsWith("--")) {
//                        plainText.append(line);
//                        line = getmsg();
//                    }
//                    if (base) {//进行了64编码
//                        System.out.println("获得的base64码:\n" + String.valueOf(plainText));
//                        System.out.println("即将进行base64解码:" + charset);
//                        from64(String.valueOf(plainText), charset);
//                    } else {
//                        System.out.println(plainText + charset);
//                    }
//                    System.out.println("html正文结束:" + plainText);
//                } else if (msg.equals(".")) {
//                    break;
//                } else {
//                    send("250 OK Sender");
//                }
//            }
//            System.out.println("结束了");
//            send("250 OK Receiver" + "\r\n");
//            getmsg();
//            send("221 smtp.slovety.top Service closing transmission channel ！GoodBye...");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (server != null) {
//                try {
//                    server.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (socket != null) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    //    public void receive(){
////        count++;
////        String str = null;
////        try {
////            if(!(str = reader.readLine()).trim().equals("")){
////                System.out.println(count + "in：" + str);
////            }
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//    public String getmsg() {
//        if (in.hasNextLine()) {
//            String line = in.nextLine();
//            System.out.println("remote:" + line);
//            return line;
//        }
//        return null;
//    }
//
//    /**
//     * 读取正文并保存到文件
//     */
////    public void receiveData(){
////        char[] temp = new char[512];
////        String str = null;
////        int count = 0;
////        try {
////            while(!(str = reader.readLine()).trim().equals(".")){
////                count++;
////                System.out.println("收到客户端Date信息：" + str);
////                log("MailReceiver","Info","收到客户端信息：" + str.trim());
////                // 消除开头换行
////                if(count >= 4 && count <=11){
////
////                }else {
////
////                }
////
////            }
////            System.out.println("收到客户端Date信息：" + str);
////            log("MailReceiver","Info","收到客户端信息：" + str.trim());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////    }
//    public void send(String str) {
//        log("sender:" + str.trim() + "\r\n");
//        pw.print(str + "\r\n");
//        pw.flush();
//    }
//
//    public static void main(String[] args) {
//        Receive mr = new Receive();
//        while (true) {
//            mr.receiveMail();
//        }
//    }
//
//    public void log(String... s) {
//        for (int i = 0; i < s.length; i++) {
//            System.out.printf(s[i]);
//        }
//        System.out.println();
//    }
//
//    static final Base64.Decoder decoder = Base64.getMimeDecoder();
//
//    public String from64(String x, String code) {
//        try {
//
//            String text = new String(decoder.decode(x.replaceAll("\n", "")), code);
//            System.out.println("内容是:" + text);
//            return text;
//        } catch (Exception e) {
//            return "出现了异常";
//        }
////        try {
//////            String text = "Java 8 Base64 编码解码 - Java8新特性";
//////            String base64encodedString = Base64.getUrlEncoder().encodeToString(text.getBytes("utf-8"));
//////            System.out.println(base64encodedString);
////            byte[] base64decodedBytes = Base64.getUrlDecoder().decode(x);
////            System.out.println("内容是:" + new String(base64decodedBytes, code));
////        } catch (UnsupportedEncodingException e) {
////            System.out.println("异常：" + e.getMessage());
////        }
//    }
//
//    public String getChatSet(String input) {
//        String charset = "";
//        if (input.contains("\"")) {
//            String pattern = "charset=\"([^\"]+)\"";
//            Pattern regex = Pattern.compile(pattern);
//            Matcher matcher = regex.matcher(input);
//            if (matcher.find()) {
//                charset = matcher.group(1);
//                return charset;
//            }
//        } else {
//            String pattern = "charset=([a-zA-Z0-9-]+)";
//
//            Pattern regex = Pattern.compile(pattern);
//            Matcher matcher = regex.matcher(input);
//
//            if (matcher.find()) {
//                charset = matcher.group(1);
//                return charset;
//            }
//        }
//        return "UTF-8";
//    }
//    public void getFrom(String str) {
////        String code = "", name = "", addr = "";
////        if (str.contains("=?")) {//采用了编码
////            String codeP = "=?\\?(.*?)\\?B?";//获得编码
////            String nameP = "\\?B?\\?(.*?)\\?=";//获得名称
////            String addrP = " <(.*?)>";//获得邮件
////
////            Matcher matcher = Pattern.compile(codeP).matcher(str);
////            if (matcher.find()) {
////                code = matcher.group(1);
////            }
////            matcher = Pattern.compile(nameP).matcher(str);
////
////            if (matcher.find()) {
////                String nameBase = matcher.group(1);
////                name = from64(nameBase, code);
////            }
////            matcher = Pattern.compile(addrP).matcher(str);
////            if (matcher.find()) {
////                addr = matcher.group(1);
////            }
////        } else {//没有采用编码，可以直接处理
////
////            String addrP = " <(.*?)>";//获得邮件
////            String nameP = "From: (.*?) <";//获得名称
////            Matcher matcher = Pattern.compile(nameP).matcher(str);
////            if(matcher.find()){
////                name = matcher.group(1);
////            }
////            matcher = Pattern.compile(addrP).matcher(str);
////            if(matcher.find()){
////                addr = matcher.group(1);
////            }
////        }
////        receiveEmail.setFrom(addr);
////        receiveEmail.setNickname(name);
//    }
//
//
//    public void getTo(String str) {
////        String code = "", name = "", addr = "";
////        if (str.contains("=?")) {//采用了编码
////            String codeP = "=?\\?(.*?)\\?B?";//获得编码
////            String nameP = "\\?B?\\?(.*?)\\?=";//获得名称
////            String addrP = " <(.*?)>";//获得邮件
////            Matcher matcher = Pattern.compile(codeP).matcher(str);
////            if(matcher.find()){
////                code = matcher.group(1);
////            }
////            matcher = Pattern.compile(nameP).matcher(str);
////            if(matcher.find()){
////                String nameBase = matcher.group(1);
////                name = baseUtils.getFrom64(nameBase, code);
////            }
////            matcher = Pattern.compile(addrP).matcher(str);
////            if(matcher.find()){
////                addr = matcher.group(1);
////            }
////            //TODO:接收者的name暂时没有处理
////        } else if (str.contains("<")) {//没有采用编码，但是有<>
////
////            String addrP = " <(.*?)>";//获得邮件
////            String nameP = "To: (.*?) <";//获得名称
////            Matcher matcher = Pattern.compile(nameP).matcher(str);
////            if(matcher.find()){
////                name = matcher.group(1);
////            }
////            matcher = Pattern.compile(addrP).matcher(str);
////            if(matcher.find()){
////                addr = matcher.group(1);
////            }
////
////        } else {//什么都没有的
////            String[] split = str.split(" ");
////            addr = split[1];
////        }
////        receiveEmail.setTo(addr);
//    }
//
//    public void getSubject(String str) {
////        String code = "", theme = "";
////        if (str.contains("=?")) {//采用了编码
////            String codeP = "=?\\?(.*?)\\?B?";//获得编码
////            String themeP = "\\?B?\\?(.*)";//获得名称
////            Matcher matcher = Pattern.compile(codeP).matcher(str);
////            if(matcher.find()){
////                code = matcher.group(1);
////            }
////            matcher = Pattern.compile(themeP).matcher(str);
////            if(matcher.find()){
////                String themeBase = matcher.group(1);
////                theme = baseUtils.getFrom64(themeBase, code);
////            }
////        } else {//没有采用编码，可以直接处理
////
////            String themeP = ": (.*)";//获得邮件
////            Matcher matcher = Pattern.compile(themeP).matcher(str);
////            if(matcher.find()){
////                theme = matcher.group(1);
////            }
////        }
////        receiveEmail.setSubject(theme);
//    }
//
//
//
//
//}