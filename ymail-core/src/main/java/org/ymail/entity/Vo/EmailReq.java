package org.ymail.entity.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ymail.entity.Email;
import org.ymail.entity.UploadFile;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailReq extends Email {
    /**
     * 附件
     */
    List<UploadFile> attachments;
}
