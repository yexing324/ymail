package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ymail.entity.Email;
import org.ymail.entity.SEmail;
import org.ymail.service.SEmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/sender")
public class SendController {
    private final SEmailService emailService;
    @PostMapping("/sendEmail")

    public void sendEmail(@RequestBody Email email){
        emailService.sendEmail(email);
    }
}
