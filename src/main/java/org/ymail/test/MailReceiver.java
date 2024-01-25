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