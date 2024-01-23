package org.ymail.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ymail.entity.Email;
import org.ymail.entity.SendEmail;
import org.ymail.enums.EmailStatus;
import org.ymail.mapper.EmailMapper;
import org.ymail.mapper.SEmailMapper;
import org.ymail.mq.MQProducer;
import org.ymail.service.SendEmailService;
import org.ymail.utils.EmailCheck;
import org.ymail.utils.Result;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SendEmailServiceImpl implements SendEmailService {
    private final EmailMapper emailMapper;
    private final MQProducer mqProducer;

    /**
     * 发送邮件，应该发送给目标服务器
     * 用户发送邮件，此时用户可以查询到未发送成功的邮件
     * 该邮件进入发送队列
     * 尝试发送，如果发送失败，则继续队列排队
     * 发送成功，对数据库进行刷新
     * @param email 邮件
     */
    @Override
    public Result<Void> sendEmail(Email email) {
        //TODO:检查email格式是否正确
        EmailCheck.checkEmail(email);
        email.setStatus(EmailStatus.SEND_READY.getValue());
        //发送到mq并写入数据库
        mqProducer.sendMsg(JSON.toJSONString(email));
        emailMapper.insert(email);
        //直接返回结果
        return Result.success();
    }

}
