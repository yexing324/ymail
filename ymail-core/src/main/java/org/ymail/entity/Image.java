package org.ymail.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片实例
 */

@Data
@NoArgsConstructor
public class Image {
    String name;
    String imageId;
    String imageType="Content-Type: image/jpeg;";
    String imagePosition="Content-Disposition: inline;";
    String cid="Content-ID: <";
    String url;

    public String getCid() {
        return cid+imageId+">";
    }
}
