package org.ymail.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceiveEmail extends Email {
    /**
     * 真实的email地址
     */
    String realFrom;
    /**
     * 真实的发送地址
     */
    String messageId;

    /**
     * 结束标识
     * 暂定
     */
    String end = "------=_Part_31565_1009936633.1705838186609";

    @Override
    public String toString() {
        return "ReceiveEmail{" +
                "realFrom='" + realFrom + '\'' +
                ", messageId='" + messageId + '\'' +
                ", end='" + end + '\'' +
                ", nickname='" + nickname + '\'' +
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
