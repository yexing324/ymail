package org.ymail.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ymail.entity.Image;
import org.ymail.entity.SendEmail;

import java.util.ArrayList;
import java.util.UUID;

/**
 * 将html中的src链接地址进行转化
 */
public class HtmlUtil {

    public static String processHtml(SendEmail sendEmail) {
        sendEmail.setImageList(new ArrayList<>());

        Document doc = Jsoup.parse(sendEmail.getHtmlText());

        // 找到所有img标签
        Elements imgTags = doc.select("img");

        // 遍历处理
        for (Element imgTag : imgTags) {
            Image image = new Image();
            String imgSrc = imgTag.attr("src");
            image.setUrl(imgSrc);
            image.setName(imgSrc.substring(imgSrc.indexOf("=")+1));
            String imageId="image-"+UUID.randomUUID();
            image.setImageId(imageId);
            sendEmail.getImageList().add(image);
            // 替换src属性
            imgTag.attr("src", "cid:"+imageId);

        }
        return doc.html();
    }

    public static void main(String[] args) {
//        MimeMultipart mimeMultipart=new MimeMultipart();
    }

}