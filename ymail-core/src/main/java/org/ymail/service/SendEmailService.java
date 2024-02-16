package org.ymail.service;

import org.ymail.entity.Vo.EmailReq;
import org.ymail.utils.Result;

public interface SendEmailService {
    Result<Void> sendEmail(EmailReq email);
}
