package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
