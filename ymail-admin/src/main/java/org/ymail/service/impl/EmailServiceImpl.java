package org.ymail.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ymail.entity.Attachment;
import org.ymail.entity.Email;
import org.ymail.enums.EmailStatus;
import org.ymail.filter.UserContext;
import org.ymail.mapper.AttachMapper;
import org.ymail.mapper.EmailMapper;
import org.ymail.resp.EmailResp;
import org.ymail.service.EmailService;
import org.ymail.util.Result;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailMapper emailMapper;
    private final AttachMapper attachMapper;
    /**
     * 获得用户登录时的具体信息
     * @return 信息
     */
    @Override
    public Result<Void> getLoginInfo() {

        System.out.println("执行到此");
        System.out.println(UserContext.getUserPhone());
        System.out.println(UserContext.getUserMail());
        return null;
    }

    @Override
    public Result<Object> getMessage() {
        LambdaQueryWrapper<Email>queryWrapper=new LambdaQueryWrapper<Email>()
                .eq(Email::getTo,UserContext.getUserMail())
                .eq(Email::getDelFlag,0)
                .orderByDesc(Email::getUpdateTime);

        List<Email> emails = emailMapper.selectList(queryWrapper);
        return Result.success(emails);
    }

    @Override
    public Result<EmailResp> readEmail(String emailId) {
        Email email=emailMapper.selectOne(new LambdaQueryWrapper<Email>()
                .eq(Email::getDelFlag,0)
                .eq(Email::getId,emailId));
        EmailResp emailResp= BeanUtil.copyProperties(email,EmailResp.class);
        emailResp.setStatusText(EmailStatus.getValueByKey(emailResp.getStatus()));

        if(StrUtil.isNotBlank(emailResp.getAttachmentId())){
            //初始化附件
            LambdaQueryWrapper<Attachment> queryWrapper = new LambdaQueryWrapper<Attachment>()
                    .eq(Attachment::getDelFlag, 0)
                    .eq(Attachment::getId, emailResp.getAttachmentId());
            List<Attachment> attachments = attachMapper.selectList(queryWrapper);
            emailResp.setAttachments(attachments);
        }

        return Result.success(emailResp);
    }
}
