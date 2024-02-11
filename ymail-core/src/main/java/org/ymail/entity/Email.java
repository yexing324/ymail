package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
/**
 * 邮件实体类
 * 接收和发送实体类共用
 * 由于进行统一管理，放在一个表里面比较方便，可以分库分表
 */
//TODO:附件对应表
@TableName("t_email_from")
@Data
@ToString
public class Email {
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
    String group;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 删除标识
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

}
