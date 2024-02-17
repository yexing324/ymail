package org.ymail.service;

import org.ymail.resp.EmailResp;
import org.ymail.util.Result;

public interface EmailService {
    Result<Void> getLoginInfo();

    /**
     * 收信接口
     * @return 收件信息
     */
    Result<Object> getMessage();

    Result<EmailResp> readEmail(String emailId);
}
