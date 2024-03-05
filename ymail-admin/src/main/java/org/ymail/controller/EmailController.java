package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;
import org.ymail.entity.Email;
import org.ymail.entity.EmailReport;
import org.ymail.resp.EmailResp;
import org.ymail.service.EmailService;
import org.ymail.util.Result;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/email")
public class EmailController {
    private final EmailService emailService;

    /**
     * 更像是获得收件箱信息
     * @return 收件箱
     */
    @GetMapping("/getMessage")
    public Result<Object> getMessage(){
        return emailService.getMessage();
    }

    @GetMapping("/getSendBox")
    public Result<Object> getSendBox(){
        return emailService.getSendBox();
    }
    @GetMapping("/getEmailByGroup")
    public Result<Object> getEmailByGroup(String group,int page,int size){
        return emailService.getEmailByGroup(group,page,size);
    }

    @GetMapping("/readEmail")
    public Result<EmailResp> readEmail(String emailId){
        return emailService.readEmail(emailId);
    }
    @PostMapping("/deleteEmail")
    public Result<Void> deleteEmail(@RequestBody List<Email> deleteEmail){
        return emailService.deleteEmail(deleteEmail);
    }
    @PostMapping("/markRead")
    public Result<Void> markRead(@RequestBody List<Email> markReadEmail){
        return emailService.markRead(markReadEmail);
    }
    @PostMapping("/markNotRead")
    public Result<Void> markNotRead(@RequestBody List<Email> markNotReadEmail){
        return emailService.markNotRead(markNotReadEmail);
    }
    @PostMapping("/markAllRead")
    public Result<Void> markAllRead(){
        return emailService.markAllRead();
    }
    @PostMapping("/moveEmailGroup")
    public Result<Void> moveGroup(@RequestBody List<Email> moveEmailGroup, String group){
        return emailService.moveEmailGroup(moveEmailGroup,group);
    }

    @PostMapping("/reportEmail")
    public Result<Void> reportEmail(@RequestBody List<Email> emails,String reason){
        return emailService.reportEmail(emails,reason);
    }
}
