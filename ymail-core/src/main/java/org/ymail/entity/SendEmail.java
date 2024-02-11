package org.ymail.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ymail.entity.Vo.EmailVo;

import java.util.List;

/**
 * 发送邮件实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendEmail extends EmailVo {
    /**
     * 准备发送的from数据
     */
    String sendFrom;
    /**
     * 准备发送的to数据
     */
    String sendTo;
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
     * image list
     */
    List<UploadFile> uploadFileList;
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
    String end = "----=_NextPart_65AD06B2_12B47D90_15F36F1D";
    String end1 = "----=_NextPart_65AD06B3_12B47D90_15F36F1D";

    @Override
    public String toString() {
        return "SendEmail{" +
                "messageId='" + messageId + '\'' +
                ", plainType='" + plainType + '\'' +
                ", HtmlType='" + HtmlType + '\'' +
                ", PlainBase64='" + PlainBase64 + '\'' +
                ", HtmlBase64='" + HtmlBase64 + '\'' +
                ", end='" + end + '\'' +
                ", from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                ", subject='" + getSubject() + '\'' +
                ", plainText='" + getPlainText() + '\'' +
                ", htmlText='" + getHtmlText() + '\'' +
                ", imageList='" + uploadFileList + '\'' +
                ", attachments='" + getAttachments() + '\'' +
                ", status=" + getStatus() +
                ", group='" + getGroup() + '\'' +
                '}';
    }
}
