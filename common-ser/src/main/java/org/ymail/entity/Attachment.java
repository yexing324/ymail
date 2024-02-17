package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.ymail.database.BaseDO;

/**
 * 附件实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("t_attachment")
public class Attachment extends BaseDO {
    String id;
    String name;
    String url;
    String type;
}
