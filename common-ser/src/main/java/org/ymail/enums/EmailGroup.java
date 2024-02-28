package org.ymail.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 邮件的发送状态
 */
@Getter
@RequiredArgsConstructor
public enum EmailGroup {
    /**
     * 收件箱
     */
    RECEIVE_BOX(0,"收件箱"),
    SEND_BOX(1,"已发送"),
    STAR_BOX(2,"星标邮件"),
    DRAFT_BOX(3,"草稿箱"),
    TODO_BOX(4,"待办邮件"),
    DELETE_BOX(5,"已删除"),
    RUBBISH_BOX(6,"垃圾箱");
    private int key;
    private String value;

    EmailGroup(int i, String s) {
        this.key=i;
        this.value=s;
    }
    public static String getValueByKey(int i){
        EmailGroup[] statuses = EmailGroup.values();
        for (EmailGroup s : statuses) {
            if (s.getKey() == i) {
                return s.getValue();
            }
        }
        return "未知";
    }
}
