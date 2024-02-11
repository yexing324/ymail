package org.ymail.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class UploadResp {
    int errno=0;
    String url;
    String name;
}
