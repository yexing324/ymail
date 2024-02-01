package org.ymail.service;

import org.ymail.entity.Email;
import org.ymail.utils.Result;

public interface SendEmailService {
    Result<Void> sendEmail(Email email);
}
