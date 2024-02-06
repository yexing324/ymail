package org.ymail.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上下文获取用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户地址
     */
    private String mail;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户 Token
     */
    private String token;
}