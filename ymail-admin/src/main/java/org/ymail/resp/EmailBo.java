package org.ymail.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ymail.entity.Attachment;
import org.ymail.entity.Email;

import java.util.List;

/**
 * 邮件详情实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class EmailBo extends Email  {
    /**
     * 邮件状态文字
     */
    String statusText;
    /**
     * 邮件附件信息
     */
    List<Attachment>attachments;
    /**
     * 是否为普通消息
     */
    boolean isMessage=true;
    String titleName;
    public EmailBo(String name){
        this.titleName=name;
        isMessage=false;
    }

}
