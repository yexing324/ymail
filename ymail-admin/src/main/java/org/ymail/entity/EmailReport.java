package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.ymail.database.BaseDO;
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_report")
public class EmailReport extends BaseDO {
    String id;
    @NotBlank(message = "举报邮件不能为空")
    String emailId;
    @NotBlank(message = "举报原因不能为空")
    String reason;
}
