package org.ymail.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 发送邮件实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendEmail extends Email{
    /**
     * Message-ID
     */
    String messageId;
    /**
     * text/html;charset=;
     */
    String plainType;
    /**
     * 同上
     */
    String HtmlType;
    /**
     * plain是否进行base编码
     * Content-Type-Transfer-Encoding
     */
    String PlainBase64;
    /**
     * html是否进行base编码
     */
    String HtmlBase64;
    /**
     * 结束标识
     * 暂定
     */
    String end="------=_Part_31565_1009936633.1705838186609";

    @Override
    public String toString() {
        return "SendEmail{" +
                "messageId='" + messageId + '\'' +
                ", plainType='" + plainType + '\'' +
                ", HtmlType='" + HtmlType + '\'' +
                ", PlainBase64='" + PlainBase64 + '\'' +
                ", HtmlBase64='" + HtmlBase64 + '\'' +
                ", end='" + end + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", plainText='" + plainText + '\'' +
                ", htmlText='" + htmlText + '\'' +
                ", status=" + status +
                ", group='" + group + '\'' +
                '}';
    }
}
