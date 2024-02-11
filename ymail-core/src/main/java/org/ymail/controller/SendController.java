package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ymail.entity.Email;
import org.ymail.entity.Vo.EmailVo;
import org.ymail.service.SendEmailService;
import org.ymail.utils.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/sender")
public class SendController {
    private final SendEmailService emailService;
    @PostMapping("/sendEmail")

    public Result<Void> sendEmail(@RequestBody EmailVo email){
//        System.out.println(email);
//        return new Result<>();
        return emailService.sendEmail(email);
    }
}
