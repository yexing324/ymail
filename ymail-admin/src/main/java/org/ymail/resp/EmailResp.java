package org.ymail.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ymail.entity.Attachment;
import org.ymail.entity.Email;

import java.util.List;

/**
 * 邮件详情实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmailResp extends Email {
    /**
     * 邮件状态文字
     */
    String statusText;
    /**
     * 邮件附件信息
     */
    List<Attachment>attachments;

}
