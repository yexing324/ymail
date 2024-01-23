package org.ymail.utils;

import org.springframework.stereotype.Component;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.InetAddress;
import java.util.Base64;
import java.util.Hashtable;
@Component
public class BaseUtils {
    static final Base64.Decoder decoder = Base64.getMimeDecoder();
    public String getFrom64(String ...str){
        String code=str.length>1?str[1]:"gb18030";
        try{
            return new String(decoder.decode(str[0].replaceAll("\n", "")), code);
        }catch (Exception ignored){
            throw new RuntimeException("解码失败");
        }
    }
    public String getBase64(String ...str){
        try {
            String code=str.length>1?str[1]:"gb18030";
            return Base64.getUrlEncoder().encodeToString(str[0].getBytes(code));
        }catch (Exception ignored){
            throw new RuntimeException("转码失败");
        }

    }
    //获得mx地址
    public String getMailHost(String host) {
        InetAddress[] records;
        try {
            records = getMXRecords(host);
        }catch (Exception e){
            throw new RuntimeException("对方服务器mx查询失败");
            //TODO:更细该邮件状态，服务不可达
        }
        InetAddress record = records[0];
        String s= record.toString().split("/")[0];
        return s.substring(0,s.length()-1);
    }
    public static InetAddress[] getMXRecords(String domainName) throws Exception {

        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ctx = new InitialDirContext( env );

        Attributes attrs = ctx.getAttributes(domainName, new String[] { "MX" });
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
}
