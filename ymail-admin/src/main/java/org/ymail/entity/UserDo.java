package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.ymail.database.BaseDO;

@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
@Data
@ToString
public class UserDo extends BaseDO {
    /**
     * id
     */
    private Long id;
    /**
     * 用户邮箱
     */
    @NotBlank(message = "账号不能为空",groups = {Register.class,login.class})
    private String mail;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",groups = {Register.class,login.class})
    private String password;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空",groups = Register.class)
    private String phone;

    /**
     * 头像名称
     */
    private String avatarName;

    /**
     * 昵称
     */
    private String nickname;


    /* 分组校验 */
    public @interface Register {}
    public @interface login {}

}
