package org.ymail.entity.Vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.ymail.entity.UserDo;
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class UserVo extends UserDo {
    /**
     * code
     */
    @NotBlank(message = "验证码不能为空",groups = Register.class)
    private String code;
}
