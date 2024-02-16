package org.ymail.utils;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.ymail.entity.SendEmail;
import org.ymail.entity.Vo.EmailReq;

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
    private final String defaultCode = "utf-8";

    public SendEmail initSendEmail(EmailReq email) {
        //TODO:校验是否有unicode
        //全当unicode
        SendEmail sendEmail = BeanUtil.copyProperties(email,SendEmail.class);
        //初始化要发送的from
        sendEmail.setSendFrom(initFrom(sendEmail.getNickname(), sendEmail.getFrom()));
        //初始化To
        sendEmail.setSendTo(initSendTo(sendEmail.getTo()));
        //初始化subject
        sendEmail.setSubject(initSubject(sendEmail.getSubject()));
        //初始化message-id
        sendEmail.setMessageId(initMessageId());
        //初始化plainTextType
        sendEmail.setPlainType(initPlainType());
        //初始化htmlTextType
        sendEmail.setHtmlType(initHtmlType());
        //初始化编码与否
        sendEmail.setPlainBase64("Content-Transfer-Encoding: base64");
        sendEmail.setHtmlBase64("Content-Transfer-Encoding: base64");

        //初始化图片相关信息
        sendEmail.setHtmlText(initImage(sendEmail));

        //初始化plain&&html内容
        sendEmail.setPlainText(initText(sendEmail.getPlainText()));
        sendEmail.setHtmlText(initText(sendEmail.getHtmlText()));

        return sendEmail;
    }

    private String initSendTo(String to) {
        return "To: "+to;
    }

    public static boolean ifHasUnicode(String str) {
        String regex = "\\p{L}";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        return matcher.find();
    }

    private String initFrom(String... str) {
        //由于已经检查了合法性，只需要nickname进行初始化
        String name = str[0];
        String base64Name = baseUtils.getBase64(name);
        return "From: \"=?" + defaultCode + "?B?" + base64Name +
                "?=\" <" + str[1] + ">";
    }

    private String initSubject(String str) {
        String base64Str = baseUtils.getBase64(str);
        return "Subject: =?" + defaultCode + "?B?" + base64Str + "?=";
    }

    private String initPlainType() {
        return "Content-Type: text/plain; charset=utf-8;";
    }

    private String initHtmlType() {
        return "Content-Type: text/html; charset=utf-8;";
    }

    private String initText(String str) {
        return baseUtils.getBase64(str, defaultCode);
    }

    private String initRealFrom(String str) {
        return getMesFromPattern(str, " <(.*?)>");
    }

    private String initMessageId() {
        return "Message-ID: <" + UUID.randomUUID() + "@mail.jinnrry.com" + ">";
    }

    private String initImage(SendEmail sendEmail){
        //TODO:应该用正则匹配
        if (!sendEmail.getHtmlText().contains("<img ")) return sendEmail.getHtmlText();
        //修改内部的imageList
        return HtmlUtil.processHtml(sendEmail);
    }

}
