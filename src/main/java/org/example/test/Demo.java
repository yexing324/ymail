package org.example.test;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.InetAddress;
import java.util.Hashtable;

public class Demo {
    public static void main(String[] args) throws Exception {
        System.out.println(getMailHost("163.com"));
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