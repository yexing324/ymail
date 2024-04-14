package org.ymail.resp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class EmailResp {
    /**
     * 置顶邮件
     */
    List<EmailBo> pinnedEmailList=new ArrayList<>();
    /**
     * 今日邮件
     */
    List<EmailBo>todayEmailList=new ArrayList<>();
    /**
     * 昨日邮件
     */
    List<EmailBo>yesterdayEmailLis=new ArrayList<>();
    /**
     * 更早的邮件
     */
    List<EmailBo>previousEmailList=new ArrayList<>();
}
