package org.ymail.entity.Vo;

import lombok.Data;

@Data
public class GroupVo {
    private String group;
    private int unread;
    private int total;
    private boolean ifSystemGroup;
}
