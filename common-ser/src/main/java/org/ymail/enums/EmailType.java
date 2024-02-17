package org.ymail.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 邮件的Content-Type
 */
@Getter
@RequiredArgsConstructor
public enum EmailType {
    mixed(0),
    related(1),
    alternative(2);

    private final int value;
}
