package org.ymail.service;

import org.ymail.entity.Email;
import org.ymail.resp.EmailResp;
import org.ymail.util.Result;

import java.util.List;

public interface EmailService {
    Result<Void> getLoginInfo();

    /**
     * 收信接口
     * @return 收件信息
     */
    Result<Object> getMessage();

    Result<EmailResp> readEmail(String emailId);

    Result<Void> deleteEmail(List<Email> deleteEmail);

    Result<Void> markRead(List<Email> markReadEmail);

    Result<Void> markNotRead(List<Email> markNotReadEmail);

    Result<Void> markAllRead();

    Result<Object> getSendBox();

    Result<Object> getEmailByGroup(String group);

    Result<Void> moveEmailGroup(List<Email>emails,String group);
}
