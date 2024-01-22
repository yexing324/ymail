package org.ymail.test;
//import com.alibaba.fastjson.JSON;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
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
                host=getMailHost(host);
            }
            server = new Socket(host, 25);
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
                from = "yexingyun@pmail.slovety.top";
            send("MAIL FROM:<" + from + ">");

            receive();
            System.out.print("input to:");
            String to = sc.nextLine();
            if (to.equals("1"))
                to = "yexing195@163.com";

            else if(to.equals("2"))
                to="2420233025@qq.com";

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
            send("h=From:To:Subject:Date;");
            send("From: \"=?gb18030?B?eWV4aW5n?=\" <yexing@pmail.slovety.top>");
            send("To: =?gb18030?B?0KHQ3MOo?= <yexing195@163.com>");
            send("Subject: this is a theme");
            send("Message-ID: <jinnrry_6E9F1A3FAB01D9AF1D200AFB4E40484C0407@jinnrry.com>");
            send("Content-Type: text/plain;");
            send("charset=\"gb18030\"");
            send("Content-Transfer-Encoding: base64");
            send("");
            send("aGVsbG8sdGhpcyBpcyBhIHRlc3QgZW1haWwNCg0KDQrQodDcw6gNCjI0MjAyMzMwMjVAcXEu");
            send("Y29tDQoNCg0KDQombmJzcDs=");
            send("");
            send("-----=_NextPart_65AD06B2_12B47D90_15F36F1D");

//            String message = sc.nextLine();
////            String[] split = message.split("/");
////            send("MessageId:5877887");
////            send("Subject:"+split[0]);
////            send(split[1]);
//            send(message);
//            send("--sdfsda");
            send(".");

//            Email e=new Email();
//            e.Subject=split[0];
//            e.Text=split[1];
//            out.print(Arrays.toString(JSON.toJSONBytes(e)) + "\r\n");
//            out.flush();
//            send(".");
            receive();
            receive();
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


    public static InetAddress[] getMXRecords(String domainName) throws Exception {

        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext( env );

        Attributes attrs = ictx.getAttributes(domainName, new String[] { "MX" });
        Attribute attr = attrs.get("MX");

        // 解析结果
        InetAddress[] addresses = new InetAddress[attr.size()];
        for (int i = 0; i < attr.size(); i++) {
            String mxRecord = (String)attr.get(i);
            String[] mxParts = mxRecord.split(" ");
            String host = mxParts[1];
            addresses[i] = InetAddress.getByName(host);
        }

        return addresses;
    }
    public static String  getMailHost(String host) throws Exception {
        InetAddress[] records = getMXRecords(host);
        InetAddress record = records[0];
        System.out.println(record);
        String s= record.toString().split("/")[0];
        return s.substring(0,s.length()-1);
    }
}