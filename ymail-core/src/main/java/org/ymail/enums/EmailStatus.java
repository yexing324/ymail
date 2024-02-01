package org.ymail.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 邮件的发送状态
 */
@Getter
@RequiredArgsConstructor
public enum EmailStatus {
    /**
     * 准备向对方邮箱投递
     */
    SEND_READY(-3),
    /**
     * 投递失败
     */
    SEND_FAILED(-2),
    /**
     * 投递成功
     */
    SEND_SUCCESS(-1),
    /**
     * 未读邮件
     */
    READ_NOT(1),
    /**
     * 已读邮件
     */
    READ_ALREADY(2);

    private final int value;
}
