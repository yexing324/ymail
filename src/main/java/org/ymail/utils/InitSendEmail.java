package org.ymail.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.ymail.entity.Email;
import org.ymail.entity.SendEmail;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ymail.utils.EmailCheck.getMesFromPattern;

/**
 * email并不是可以直接发送的邮件格式
 * 因此需要进行初始化
 */
@AllArgsConstructor
@Component
public class InitSendEmail {
    private final BaseUtils baseUtils;
    private final String defaultCode="utf-8";

    public SendEmail initSendEmail(Email email) {
        //TODO:校验是否有unicode
        //全当unicode
        SendEmail sendEmail = new SendEmail();
        //保留地址
        sendEmail.setRealFrom(initRealFrom(email.getFrom()));
        sendEmail.setRealTo(email.getTo());
        //初始化from
        sendEmail.setFrom(initFrom(email.getFrom()));
        //初始化To
        sendEmail.setTo(initTo(email.getTo()));
        //初始化subject
        sendEmail.setSubject(initSubject(email.getSubject()));
        //初始化message-id
        sendEmail.setMessageId(initMessageId());
        //初始化plainTextType
        sendEmail.setPlainType(initPlainType());
        //初始化htmlTextType
        sendEmail.setHtmlType(initHtmlType());
        //初始化编码与否
        sendEmail.setPlainBase64("Content-Transfer-Encoding: base64");
        sendEmail.setHtmlBase64("Content-Transfer-Encoding: base64");
        //初始化plain&&html内容
        sendEmail.setPlainText(initText(email.getPlainText()));
        sendEmail.setHtmlText(initText(email.getHtmlText()));

        return sendEmail;
    }

    public static boolean ifHasUnicode(String str) {
        String regex = "\\p{L}";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        return matcher.find();
    }

    private String initFrom(String str) {
        //由于已经检查了合法性，只需要nickname进行初始化
        String[] split = str.trim().split(" ");
        String name = split[0];
        String base64Name = baseUtils.getBase64(name);
        return "From: \"=?" + defaultCode + "?B?" + base64Name +
                "?=\" " + split[1] ;
    }

    private String initTo(String str) {
        return "To: " + str;
    }

    private String initSubject(String str) {
        String base64Str = baseUtils.getBase64(str);
        return "Subject: =?" + defaultCode + "?B?" + base64Str+"?=";
    }

    private String initPlainType() {
        return "Content-Type: text/plain; charset=utf-8;";
    }

    private String initHtmlType() {
        return "Content-Type: html/plain; charset=utf-8;";
    }

    private String initText(String str) {
        return baseUtils.getBase64(str, defaultCode);
    }

    private String initRealFrom(String str) {
        return getMesFromPattern(str, " <(.*?)>");
    }

    private String initMessageId() {
        return "Message-ID: <" + UUID.randomUUID() +"@mail.jinnrry.com"+ ">";
    }

}
