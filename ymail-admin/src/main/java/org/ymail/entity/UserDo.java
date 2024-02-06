package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.esotericsoftware.kryo.serializers.FieldSerializer.*;
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
     * vip
     */
    private String vipFlag;

    /* 分组校验 */
    public @interface Register {}
    public @interface login {}

}
