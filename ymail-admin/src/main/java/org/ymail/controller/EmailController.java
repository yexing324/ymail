package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ymail.entity.Email;
import org.ymail.entity.Group;
import org.ymail.enums.EmailGroup;
import org.ymail.resp.EmailBo;
import org.ymail.service.EmailService;
import org.ymail.util.Result;

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
        if(EmailGroup.RECEIVE_BOX.getValue().equals(group))
            return emailService.getEmailByReceiveGroup(group,page,size);
        else{
            return emailService.getEmailByGroup(group,page,size);
        }
    }

    @GetMapping("/readEmail")
    public Result<EmailBo> readEmail(String emailId){
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
    @PostMapping("/setEmailPinned")
    public Result<Void> setEmailPinned(@RequestBody List<Email> emails,String group){
        return emailService.setEmailPinned(emails,group);
    }

    @PostMapping("/cancelSetEmailPinned")
    public Result<Void> cancelSetEmailPinned(@RequestBody List<Email> emails,String group){
        return emailService.cancelSetEmailPinned(emails,group);
    }
    @PostMapping("/markEmailColor")
    public Result<Void> markEmailColor(@RequestBody List<Email> emails,String color){
        return emailService.markEmailColor(emails,color);
    }
    @PostMapping("/createEmailFolder")
    public Result<Void> createEmailFolder(@RequestBody List<Email> emails,String group){
        return emailService.createEmailFolder(emails,group);
    }
    @GetMapping("/getGroupList")
    public Result<List<Group>> getGroupList(){
        return emailService.getGroupList();
    }
}
