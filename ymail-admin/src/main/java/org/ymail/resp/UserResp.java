package org.ymail.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功的返回对象
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResp {
    private String mail;
    private String cookie;
    private String avatarName;
}
