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
import org.ymail.resp.EmailBo;
import org.ymail.resp.EmailResp;
import org.ymail.service.EmailService;
import org.ymail.util.MPage;
import org.ymail.util.Result;
import org.ymail.util.ThreadPool;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
     *
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
        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<Email>()
                .eq(Email::getTo, UserContext.getUserMail())
                .eq(Email::getDelFlag, 0)
                .eq(Email::getGroup, EmailGroup.RECEIVE_BOX.getValue())
                .orderByDesc(Email::getCreateTime);

        IPage<Email> page = new Page<>(1, 10);

        IPage<Email> emails = emailMapper.selectPage(page, queryWrapper);

        List<EmailBo> res = new ArrayList<>();
        emails.getRecords().forEach(u -> {
            EmailBo emailBo = BeanUtil.copyProperties(u, EmailBo.class);
            emailBo.setStatusText(EmailStatus.getValueByKey(emailBo.getStatus()));
            res.add(emailBo);
        });
        IPage<EmailBo> respIPage = new Page<>();
        respIPage.setRecords(res);
        respIPage.setTotal(emails.getTotal());
        respIPage.setCurrent(emails.getCurrent());
        respIPage.setRecords(res);
        return Result.success(respIPage);
    }

    @Override
    public Result<EmailBo> readEmail(String emailId) {
        Email email = emailMapper.selectOne(new LambdaQueryWrapper<Email>()
                .eq(Email::getDelFlag, 0)
                .eq(Email::getId, emailId));
        EmailBo emailBo = BeanUtil.copyProperties(email, EmailBo.class);
        emailBo.setStatusText(EmailStatus.getValueByKey(emailBo.getStatus()));

        if (StrUtil.isNotBlank(emailBo.getAttachmentId())) {
            //初始化附件
            LambdaQueryWrapper<Attachment> queryWrapper = new LambdaQueryWrapper<Attachment>()
                    .eq(Attachment::getDelFlag, 0)
                    .eq(Attachment::getId, emailBo.getAttachmentId());
            List<Attachment> attachments = attachMapper.selectList(queryWrapper);
            emailBo.setAttachments(attachments);
        }
        //标记为已读，异步
        ThreadPool.getThread(() -> {
            List<Email> list = new ArrayList<>();
            list.add(email);
            markRead(list);
        });


        return Result.success(emailBo);
    }

    @Override
    public Result<Void> deleteEmail(List<Email> deleteEmail) {
        if (deleteEmail.isEmpty()) {
            throw new RuntimeException("您还没有选中邮件");
        }
        try {
            for (Email email : deleteEmail) {
                email.setDelFlag(1);
                emailMapper.updateById(email);
            }
        } catch (Exception e) {
            return Result.failure("删除出现错误");
        }

        return Result.success();
    }

    @Override
    public Result<Void> markRead(List<Email> markReadEmail) {
        markReadEmail.forEach(item -> {
            item.setStatus(READ_ALREADY.getKey());
            emailMapper.updateById(item);
        });
        return Result.success();
    }

    @Override
    public Result<Void> markNotRead(List<Email> markNotReadEmail) {
        markNotReadEmail.forEach(item -> {
            item.setStatus(READ_NOT.getKey());
            emailMapper.updateById(item);
        });
        return Result.success();
    }

    @Override
    public Result<Void> markAllRead() {
        String userMail = UserContext.getUserMail();
        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<Email>()
                .eq(Email::getDelFlag, 0)
                //TODO::判断当前登录者是否为邮件拥有者
//                .eq(Email::getMaster,null)
                .eq(Email::getTo, userMail);
        //TODO::通过群组查询
//                .eq(Email::getGroup,null);
        Email email = new Email();
        email.setStatus(READ_ALREADY.getKey());
        emailMapper.update(email, queryWrapper);
        return Result.success();
    }

    @Override
    public Result<Object> getSendBox() {
        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<Email>()
                .eq(Email::getMaster, UserContext.getUserMail())
                .eq(Email::getDelFlag, 0)
                .eq(Email::getGroup, EmailGroup.SEND_BOX.getValue())
                .orderByDesc(Email::getCreateTime);

        List<Email> emails = emailMapper.selectList(queryWrapper);
        List<EmailBo> res = new ArrayList<>();
        emails.forEach(u -> {
            EmailBo emailBo = BeanUtil.copyProperties(u, EmailBo.class);
            emailBo.setStatusText(EmailStatus.getValueByKey(emailBo.getStatus()));
            res.add(emailBo);
        });
        return Result.success(res);
    }

    /**
     * 收件箱和其他的确实不一样，所以需要分开写
     * TODO:一个文件夹置顶邮件只允许出现10封
     * page最小值为1，size最小值为10
     */

    @Override
    public Result<Object> getEmailByReceiveGroup(String group, int page, int size) {
        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<>();
        //防止NPE
        List<Email> pinnedEmails = new ArrayList<>();
        List<Email> otherEmails = new ArrayList<>();
        IPage<Email> emails = new Page<>(1, 10);
        //查询置顶后，查询的总数
        long otherSize = 0;
        if (page == 1) {
            //需要置顶
            queryWrapper.eq(Email::getMaster, UserContext.getUserMail())
                    .eq(Email::getDelFlag, 0)
                    .eq(Email::isPinned, 1)
                    .eq(Email::getGroup, group)
                    .orderByDesc(Email::getCreateTime);
            pinnedEmails = emailMapper.selectList(queryWrapper);
            int pinnedSize = pinnedEmails.size();
            //置顶邮件<=size
            if (pinnedSize < size) {
                //执行到这里，page一定等于1
                otherEmails = emailMapper.selectOtherEmail(UserContext.getUserMail(), group, 0, size - pinnedSize);
                otherSize = emailMapper.selectOtherEmailSum(UserContext.getUserMail(), group);
            }
        } else {
            //不可能查出来置顶邮件
            queryWrapper = new LambdaQueryWrapper<Email>()
                    .eq(Email::getMaster, UserContext.getUserMail())
                    .eq(Email::getDelFlag, 0)
                    .eq(Email::getGroup, group)
                    .orderByAsc(Email::getCreateTime);

            IPage<Email> ipage = new Page<>(page, size);
            emails = emailMapper.selectPage(ipage, queryWrapper);
            otherEmails = emails.getRecords();
            otherSize = emails.getTotal();
        }


        EmailResp emailResp = new EmailResp();
        pinnedEmails.forEach(
                u -> {
                    EmailBo emailBo = BeanUtil.copyProperties(u, EmailBo.class);
                    emailBo.setStatusText(EmailStatus.getValueByKey(emailBo.getStatus()));
                    emailResp.getPinnedEmailList().add(emailBo);
                }
        );

        for (Email u : otherEmails) {//处理返回的邮件列表
            EmailBo emailBo = BeanUtil.copyProperties(u, EmailBo.class);
            emailBo.setStatusText(EmailStatus.getValueByKey(emailBo.getStatus()));
            //判断日期
            Date createTime = emailBo.getCreateTime();
            LocalDate updateDate = createTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();
            LocalDate yesterday = currentDate.minusDays(1);

            if (updateDate.isEqual(currentDate)) {
                // 时间是今天
                emailResp.getTodayEmailList().add(emailBo);
                continue;
            }
            if (updateDate.isEqual(yesterday)) {
                emailResp.getYesterdayEmailLis().add(emailBo);
                // 时间是昨天
                continue;
            }
            //时间是更早之前
            emailResp.getPreviousEmailList().add(emailBo);
        }

        //因为是对象类型，所以需要自定义分页
        MPage<EmailResp> respIPage = new MPage<>();
        respIPage.setData(emailResp);
        respIPage.setTotal(otherSize + pinnedEmails.size());
        respIPage.setCurrent(emails.getCurrent());
        respIPage.setSize(emails.getSize());
        respIPage.setPages(emails.getPages());
        respIPage.setCurrentTotal(pinnedEmails.size() + otherEmails.size());
        return Result.success(respIPage);
    }

    /**
     * 除了收件箱以外的邮件
     */
    @Override
    public Result<Object> getEmailByGroup(String group, int page, int size) {
        System.out.println(page + " " + size);
        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<Email>()
                .eq(Email::getMaster, UserContext.getUserMail())
                .eq(Email::getDelFlag, 0)
                .eq(Email::getGroup, group)
                .orderByDesc(Email::getCreateTime);

        IPage<Email> ipage = new Page<>(page, size);

        IPage<Email> emails = emailMapper.selectPage(ipage, queryWrapper);

        List<EmailBo> res = new ArrayList<>();
        emails.getRecords().forEach(u -> {
            EmailBo emailBo = BeanUtil.copyProperties(u, EmailBo.class);
            emailBo.setStatusText(EmailStatus.getValueByKey(emailBo.getStatus()));
            res.add(emailBo);
        });
        IPage<EmailBo> respIPage = new Page<>();
        respIPage.setRecords(res);
        respIPage.setTotal(emails.getTotal());
        respIPage.setCurrent(emails.getCurrent());
        respIPage.setRecords(res);
        respIPage.setSize(emails.getSize());
        respIPage.setPages(emails.getPages());
        return Result.success(respIPage);
    }

    @Override
    public Result<Void> moveEmailGroup(List<Email> emails, String group) {
        if (emails == null || emails.isEmpty()) {
            throw new RuntimeException("未选择邮件");
        }
        for (Email email : emails) {
            email.setGroup(group);
            emailMapper.updateById(email);
        }
        return Result.success();
    }

    @Override
    public Result<Void> reportEmail(List<Email> emails, String reason) {
        for (Email email : emails) {
            EmailReport emailReport = new EmailReport();
            emailReport.setEmailId(email.getId());
            emailReport.setReason(reason);
            emailReportMapper.insert(emailReport);
        }
        return Result.success();
    }

    @Override
    public Result<Void> setEmailPinned(List<Email> emails, String group) {
        if (emails == null || emails.isEmpty()) {
            return Result.failure("您还没有选中");
        }
        if (emails.size() >= 10) {
            return Result.failure("单个文件夹最多只能有10封邮件置顶");
        }
        LambdaQueryWrapper<Email> queryWrapper = new LambdaQueryWrapper<Email>()
                .eq(Email::getMaster, UserContext.getUserMail())
                .eq(Email::getDelFlag, 0)
                .eq(Email::getGroup, group)
                .eq(Email::isPinned, 1);
        List<Email> pinnedEmails = emailMapper.selectList(queryWrapper);
        List<String> pinnedIds = pinnedEmails.stream().map(Email::getId).toList();
        List<String> readyPinnedEmailsId = emails.stream().distinct().filter(s -> !pinnedIds.contains(s.getId())).map(Email::getId).toList();
        if (readyPinnedEmailsId.size() + pinnedIds.size() > 10) {
            return Result.failure("单个文件夹最多只能有10封邮件置顶");
        }

        //更新操作放到循环之中
        Email email = new Email();
        email.setPinned(true);
        readyPinnedEmailsId.forEach(
                emailId -> {
                    email.setId(emailId);
                    emailMapper.updateById(email);
                }
        );
        return Result.success();
    }

    @Override
    public Result<Void> cancelSetEmailPinned(List<Email> emails, String group) {
        emails.stream().distinct().forEach(
                email -> {
                    email.setPinned(false);
                    emailMapper.updateById(email);
                }
        );
        return Result.success();
    }
}