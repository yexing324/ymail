package org.ymail.service;

import org.ymail.entity.Email;
import org.ymail.entity.Vo.EmailVo;
import org.ymail.utils.Result;

public interface SendEmailService {
    Result<Void> sendEmail(EmailVo email);
}
