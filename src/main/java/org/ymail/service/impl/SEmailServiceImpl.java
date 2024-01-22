package org.ymail.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ymail.entity.Email;
import org.ymail.entity.SEmail;
import org.ymail.enums.EmailStatus;
import org.ymail.mapper.SEmailMapper;
import org.ymail.service.SEmailService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SEmailServiceImpl implements SEmailService {
    private final SEmailMapper emailMapper;

    /**
     * 发送邮件，应该发送给目标服务器
     * 用户发送邮件，此时用户可以查询到未发送成功的邮件
     * 该邮件进入发送队列
     * 尝试发送，如果发送失败，则继续队列排队
     * 发送成功，对数据库进行刷新
     * @param email 邮件
     */
    @Override
    public void sendEmail(Email email) {
        System.out.println(email);
        List<SEmail> SEmails = emailMapper.selectList(null);
        System.out.println(SEmails);
//        int value = EmailStatus.SEND_READY.getValue();
    }
}
