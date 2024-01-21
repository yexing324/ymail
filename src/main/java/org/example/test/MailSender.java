package org.example.test;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
class Email{
    public String Text;
    public String Subject;
}

public class MailSender {
    private Scanner in;
    private PrintWriter out;

    public static final int DEFAULT_WHIDTH = 300;
    public static final int DEFAULT_HEIGHT = 300;


    public void sendMail() {
        Scanner sc = new Scanner(System.in);
        Socket server = null;
        try {
            System.out.print("input host:");
            String host = sc.nextLine();
            if (host.equals("1")){
                host = "163.com";
                host="163mx01.mxmail.netease.com";
            }

            else if(host.equals("2")){
                host="qq.com";
                host="mx3.qq.com";
            }else if(host.equals("127")){
                host="127.0.0.1";
            }else{
                host=Demo.getMailHost(host);
            }
            server = new Socket(host, 28);
            InputStream ins = server.getInputStream();
            OutputStream outs = server.getOutputStream();
            in = new Scanner(ins);
            out = new PrintWriter(outs);
            String hostName="jinnrry.com";
            receive();
            send("HELO " + hostName);
            receive();
            System.out.print("input from:");
            String from = sc.nextLine();
            if (from.equals("1")||from.equals("2"))
                from = "yexingyun@jinnrry.com";
            send("MAIL FROM:<" + from + ">");

            receive();
            System.out.print("input to:");
            String to = sc.nextLine();
            if (to.equals("1"))
                to = "yexing195@163.com";

            else if(to.equals("2"))
                to="2572508697@qq.com";

            send("RCPT TO:<" + to + ">");
            receive();

//            System.out.println("****************************forwarding ehlo,entering 1 for continue");
//            sc.nextLine();
//
//            send(Base64.getEncoder().encodeToString("sender@qq.com".getBytes(StandardCharsets.UTF_8)));
//
//            send("EHLO smtp.qq.com");
//            receive();
//
//            System.out.println("****************************forwarding auth,entering 1 for continue");
//            sc.nextLine();
//
//
//            send("AUTH LOGIN");
//            receive();

            send("DATA");
            receive();
            send("Content-Type: text/plain;");
            send("charset=gb18030");
            send("base64");
            send("");
            System.out.print("input message Object and Text:");
            String message = sc.nextLine();
//            String[] split = message.split("/");
//            send("MessageId:5877887");
//            send("Subject:"+split[0]);
//            send(split[1]);
            send(message);
            send("--sdfsda");
            send(".");

//            Email e=new Email();
//            e.Subject=split[0];
//            e.Text=split[1];
//            out.print(Arrays.toString(JSON.toJSONBytes(e)) + "\r\n");
//            out.flush();
//            send(".");
            receive();
            send("QUIT");
            receive();
            Thread.sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String str) {
        System.out.println("Sender:" + str);
        out.print(str.replaceAll("\n", "\r\n") + "\r\n");
        out.flush();
    }

    private void receive() {
        if (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println("server:" + line);
        }
    }

    public static void main(String[] args) {
        new MailSender().sendMail();
    }

}