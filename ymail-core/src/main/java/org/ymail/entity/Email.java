package org.ymail.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
/**
 * 接收实体类
 */
@TableName("t_email")
@Data
@ToString
public class Email  {
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
     * 未读
     * 已读
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
