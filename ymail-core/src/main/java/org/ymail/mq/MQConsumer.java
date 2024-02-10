package org.ymail.mq;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.ymail.entity.Email;
import org.ymail.entity.SendEmail;
import org.ymail.utils.BaseUtils;
import org.ymail.utils.InitSendEmail;
import org.ymail.utils.Sender;
import org.ymail.utils.ThreadPool;

@Slf4j
@Component
@AllArgsConstructor
public class MQConsumer {
    private final InitSendEmail initSendEmail;
    private final BaseUtils baseUtils;
    private final String domain="pmail.slovety.com";
    @Service
    @RocketMQMessageListener(topic = "email", selectorExpression = "*", consumerGroup = "email_one")
    public class ConsumerSend implements RocketMQListener<String> {
        @Override
        public void onMessage(String msg) {
            Email email = JSON.parseObject(msg, Email.class);
            //执行发送逻辑
            SendEmail sendEmail = initSendEmail.initSendEmail(email);
            //准备发送
            ThreadPool.getThread(new Sender(sendEmail,baseUtils,domain));
//            log.info("监听到消息：user={}", JSON.toJSONString(email));
        }
    }

}
