package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ymail.database.BaseDO;

import java.util.Date;

/**
 * 邮件分组
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_group")
public class Group extends BaseDO {

    /**
     * 拥有者
     */
    private String master;

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private int count;
}
