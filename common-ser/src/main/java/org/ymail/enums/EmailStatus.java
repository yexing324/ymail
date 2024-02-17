package org.ymail.enums;
import lombok.*;

/**
 * 邮件的发送状态
 */
@Getter
@RequiredArgsConstructor
public enum EmailStatus {
    /**
     * 准备向对方邮箱投递
     */
    SEND_READY(-4,"正在发送"),
    /**
     * 重试
     */
    SEND_RETRY(-3,"正在重试"),
    /**
     * 投递失败
     */
    SEND_FAILED(-2,"发送失败"),
    /**
     * 投递成功
     */
    SEND_SUCCESS(-1,"发送成功"),
    /**
     * 未读邮件
     */
    READ_NOT(1,"未读"),
    /**
     * 已读邮件
     */
    READ_ALREADY(2,"已读");
    private int key;
    private String value;

    EmailStatus(int i, String s) {
        this.key=i;
        this.value=s;
    }
    public static String getValueByKey(int i){
        EmailStatus[] statuses = EmailStatus.values();
        for (EmailStatus s : statuses) {
            if (s.getKey() == i) {
                return s.getValue();
            }
        }
        return "未知";
    }
}
