package org.ymail.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ymail.entity.Attachment;
import org.ymail.entity.Email;
import org.ymail.entity.EmailReport;
import org.ymail.enums.EmailGroup;
import org.ymail.enums.EmailStatus;
import org.ymail.filter.UserContext;
import org.ymail.mapper.AttachMapper;
import org.ymail.mapper.EmailMapper;
import org.ymail.mapper.EmailReportMapper;
import org.ymail.resp.EmailResp;
import org.ymail.service.EmailService;
import org.ymail.util.Result;
import org.ymail.util.ThreadPool;

import java.util.ArrayList;
import java.util.List;

import static org.ymail.enums.EmailStatus.READ_ALREADY;
import static org.ymail.enums.EmailStatus.READ_NOT;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailMapper emailMapper;
    private final AttachMapper attachMapper;
    private final EmailReportMapper emailReportMapper;
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
                .eq(Email::getGroup, EmailGroup.RECEIVE_BOX.getValue())
                .orderByDesc(Email::getUpdateTime);

        IPage<Email>page=new Page<>(1,10);

        IPage<Email> emails = emailMapper.selectPage(page, queryWrapper);

        List<EmailResp>res=new ArrayList<>();
        emails.getRecords().forEach(u->{
            EmailResp emailResp= BeanUtil.copyProperties(u,EmailResp.class);
            emailResp.setStatusText(EmailStatus.getValueByKey(emailResp.getStatus()));
            res.add(emailResp);
        });
        IPage<EmailResp>respIPage=new Page<>();
        respIPage.setRecords(res);
        respIPage.setTotal(emails.getTotal());
        respIPage.setCurrent(emails.getCurrent());
        respIPage.setRecords(res);
        return Result.success(respIPage);
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
        //标记为已读，异步
        ThreadPool.getThread(()->{
            List<Email>list=new ArrayList<>();
            list.add(email);
            markRead(list);
        });


        return Result.success(emailResp);
    }

    @Override
    public Result<Void> deleteEmail(List<Email> deleteEmail) {
        if(deleteEmail.isEmpty()){
            throw new RuntimeException("您还没有选中邮件");
        }
        try{
            for (Email email:deleteEmail){
                email.setDelFlag(1);
                emailMapper.updateById(email);
            }
        }catch (Exception e){
            return Result.failure("删除出现错误");
        }

        return Result.success();
    }

    @Override
    public Result<Void> markRead(List<Email> markReadEmail) {
        markReadEmail.forEach(item->{
            item.setStatus(READ_ALREADY.getKey());
            emailMapper.updateById(item);
        });
        return Result.success();
    }

    @Override
    public Result<Void> markNotRead(List<Email> markNotReadEmail) {
        markNotReadEmail.forEach(item->{
            item.setStatus(READ_NOT.getKey());
            emailMapper.updateById(item);
        });
        return Result.success();
    }

    @Override
    public Result<Void> markAllRead() {
        String userMail = UserContext.getUserMail();
        LambdaQueryWrapper<Email>queryWrapper=new LambdaQueryWrapper<Email>()
                .eq(Email::getDelFlag,0)
                //TODO::判断当前登录者是否为邮件拥有者
//                .eq(Email::getMaster,null)
                .eq(Email::getTo,userMail);
                //TODO::通过群组查询
//                .eq(Email::getGroup,null);
        Email email=new Email();
        email.setStatus(READ_ALREADY.getKey());
        emailMapper.update(email,queryWrapper);
        return Result.success();
    }

    @Override
    public Result<Object> getSendBox() {
        LambdaQueryWrapper<Email>queryWrapper=new LambdaQueryWrapper<Email>()
                .eq(Email::getMaster,UserContext.getUserMail())
                .eq(Email::getDelFlag,0)
                .eq(Email::getGroup, EmailGroup.SEND_BOX.getValue())
                .orderByDesc(Email::getUpdateTime);

        List<Email> emails = emailMapper.selectList(queryWrapper);
        List<EmailResp>res=new ArrayList<>();
        emails.forEach(u->{
            EmailResp emailResp= BeanUtil.copyProperties(u,EmailResp.class);
            emailResp.setStatusText(EmailStatus.getValueByKey(emailResp.getStatus()));
            res.add(emailResp);
        });
        return Result.success(res);
    }

    @Override
    public Result<Object> getEmailByGroup(String group,int page,int size) {
        System.out.println(page+" "+size);
        LambdaQueryWrapper<Email>queryWrapper=new LambdaQueryWrapper<Email>()
                .eq(Email::getMaster,UserContext.getUserMail())
                .eq(Email::getDelFlag,0)
                .eq(Email::getGroup, group)
                .orderByDesc(Email::getUpdateTime);

        IPage<Email>ipage=new Page<>(page,size);

        IPage<Email> emails = emailMapper.selectPage(ipage, queryWrapper);

        List<EmailResp>res=new ArrayList<>();
        emails.getRecords().forEach(u->{
            EmailResp emailResp= BeanUtil.copyProperties(u,EmailResp.class);
            emailResp.setStatusText(EmailStatus.getValueByKey(emailResp.getStatus()));
            res.add(emailResp);
        });
        IPage<EmailResp>respIPage=new Page<>();
        respIPage.setRecords(res);
        respIPage.setTotal(emails.getTotal());
        respIPage.setCurrent(emails.getCurrent());
        respIPage.setRecords(res);
        respIPage.setSize(emails.getSize());
        respIPage.setPages(emails.getPages());
        return Result.success(respIPage);
    }

    @Override
    public Result<Void> moveEmailGroup(List<Email>emails,String group) {
       if(emails==null||emails.isEmpty()){
           throw new RuntimeException("未选择邮件");
       }
       for(Email email:emails){
           email.setGroup(group);
           emailMapper.updateById(email);
       }
        return Result.success();
    }

    @Override
    public Result<Void> reportEmail(List<Email> emails,String reason) {
        for (Email email:emails){
            EmailReport emailReport=new EmailReport();
            emailReport.setEmailId(email.getId());
            emailReport.setReason(reason);
            emailReportMapper.insert(emailReport);
        }
        return Result.success();
    }
}