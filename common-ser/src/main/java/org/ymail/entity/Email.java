package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ymail.database.BaseDO;

import java.util.Date;

/**
 * 邮件实体类
 * 接收和发送实体类共用
 * 由于进行统一管理，放在一个表里面比较方便，可以分库分表
 */
@EqualsAndHashCode(callSuper = true)
@TableName("t_email")
@Data
@ToString
public class Email extends BaseDO {
    /**
     * from前面的昵称
     */
    String nickname;
    @TableField("`from`")
    String from;
    @TableField("`to`")
    String to;
    @TableField("`subject`")
    String subject;
    String plainText;
    String htmlText;
    /**
     * 准备发送
     * 未成功发送
     * 成功发送
     * 参看枚举类型EmailStatus
     */
    @TableField("`status`")
    int status;

    /**
     * 默认分组
     */
    @TableField("`group`")
    String group;

}
