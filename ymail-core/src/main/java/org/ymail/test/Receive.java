//package org.ymail.test;

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
public class Receive {
    private ServerSocket server;
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader reader;
    private int count;
    private InputStream inputStream;
    protected OutputStream outputStream;

    Scanner in;
    StringBuffer s=new StringBuffer();

    public void receiveMail() {

        try {
            server = new ServerSocket(25);
            socket = server.accept();
            System.out.println("服务端启动");
            in = new Scanner(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream());

            // 写日志文件
            log("收到主机" + socket.getInetAddress() + socket.getInetAddress().getHostName() + "的连接请求！\n");
            send("220 smtp.slovety.top SMTP CSUSOFTMAIL");
            int i=0;
            while (true) {
                String msg = getmsg();
                if (msg==null){
                    send("250 OK Sender");
                    continue;
                }
                if (msg.startsWith("EHLO")) {
                    send("250 OK smtp.shexu.com");
                } else if (msg.contains("FROM")) {
                    send("250 OK Sender");
                } else if (msg.contains("TO")) {
                    send("250 OK Receiver");
                } else if (msg.startsWith("DATA")) {
                    send("354 Start mail input; end with <CRLF>.<CRLF>");
                }  else if (msg.equals(".")) {
                    break;
                }else if(msg.equals("QUIT")){
                    break;
                }
                else {
                    send("250 OK Sender");
                }
            }
            send("250 OK Sender");
            send("221 smtp.slovety.top Service closing transmission channel ！GoodBye...");
            System.out.println("\n\n\n\n\n");
            System.out.println(s.toString());

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

    public String getmsg() {
        if (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(line);
            return line;
        }
        return "位置";
    }
    public void send(String str) {
        pw.print(str + "\r\n");
        pw.flush();
    }

    public static void main(String[] args) {
        Receive mr = new Receive();
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

}