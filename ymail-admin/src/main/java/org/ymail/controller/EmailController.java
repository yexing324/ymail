package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ymail.resp.EmailResp;
import org.ymail.service.EmailService;
import org.ymail.util.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/email")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/getMessage")
    public Result<Object> getMessage(){
        return emailService.getMessage();
    }

    @PostMapping("/readEmail")
    public Result<EmailResp> readEmail(String emailId){
        System.out.println(emailId);
        return emailService.readEmail();
    }
}
