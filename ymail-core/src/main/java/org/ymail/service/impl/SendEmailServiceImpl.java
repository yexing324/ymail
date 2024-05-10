package org.ymail.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ymail.entity.Attachment;
import org.ymail.entity.Vo.EmailReq;
import org.ymail.enums.EmailGroup;
import org.ymail.enums.EmailStatus;
import org.ymail.mapper.AttachMapper;
import org.ymail.mapper.EmailMapper;
import org.ymail.mq.MQProducer;
import org.ymail.service.SendEmailService;
import org.ymail.utils.EmailCheck;
import org.ymail.utils.Result;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SendEmailServiceImpl implements SendEmailService {
    private final EmailMapper emailMapper;
    private final MQProducer mqProducer;
    private final AttachMapper attachMapper;
    private final String fileUrl="D:\\upload\\file\\";


    /**
     * 发送邮件，应该发送给目标服务器
     * 用户发送邮件，此时用户可以查询到未发送成功的邮件
     * 该邮件进入发送队列
     * 尝试发送，如果发送失败，则继续队列排队
     * 发送成功，对数据库进行刷新
     * @param email 邮件
     */
    @Override
    public Result<Void> sendEmail(EmailReq email) {
        //此时传递可以影响到该email,直接在函数中修改即可
        EmailCheck.checkEmailAndInit(email);
        email.setStatus(EmailStatus.SEND_READY.getKey());
        email.setGroup(EmailGroup.SEND_BOX.getValue());
        //TODO::应该将master设置为UserContext的mail地址，但是现在加过滤器不方便调试
        email.setMaster(email.getFrom());

        //发送到mq并写入数据库
        mqProducer.sendMsg(JSON.toJSONString(email));
        //TODO:处理与附件的关系
        //目前由于email中没有附件字段，因此还可以继续运行
        if(email.getAttachments()!=null&&!email.getAttachments().isEmpty()){
            //含有附件
            email.setAttachmentId(UUID.randomUUID().toString());
            //TODO:事务
            emailMapper.insert(email);
            for (Attachment attachment: email.getAttachments()){
                attachment.setId(email.getAttachmentId());
                attachment.setUrl(fileUrl+attachment.getName());
                attachMapper.insert(attachment);
            }
        }else{
            emailMapper.insert(email);
        }

        //直接返回结果
        return Result.success();
    }

}
