package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ymail.entity.UserDo;
import org.ymail.entity.Vo.UserVo;
import org.ymail.resp.UserResp;
import org.ymail.service.EmailService;
import org.ymail.service.UserService;
import org.ymail.util.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/admin")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    @PostMapping("/login")
    public Result<UserResp> login(@Validated(UserDo.login.class) @RequestBody UserVo reParam,String domain) {
        reParam.setMail(reParam.getMail()+domain);
        return userService.login(reParam);
    }

    @PostMapping("/register")
    public Result<Void> register(@Validated(UserDo.Register.class) @RequestBody UserVo reqParam, String domain) {
        reqParam.setMail(reqParam.getMail()+domain);
        return userService.register(reqParam);
    }

    @GetMapping("/getLoginInfo")
    public Result<Void>getLoginInfo(){
        return emailService.getLoginInfo();
    }

    @GetMapping("/sendCode")
    public Result<Void>sendCoed(String phone){
        return emailService.sendCode(phone);
    }
}
