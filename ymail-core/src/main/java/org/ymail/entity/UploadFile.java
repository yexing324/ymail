package org.ymail.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 图片实例
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UploadFile extends Attachment {
    String fileID;
    String fileType="Content-Type: image/jpeg;";
    String filePosition="Content-Disposition: inline;";
    String cid="Content-ID: <";

    public String getCid() {
        return cid+fileID+">";
    }
}
