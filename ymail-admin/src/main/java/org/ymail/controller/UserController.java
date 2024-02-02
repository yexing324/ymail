package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymail.entity.UserDo;
import org.ymail.service.UserService;
import org.ymail.util.Result;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public Result<Void> login() {
        return Result.success();
    }

    @PostMapping("/register")
    public Result<Void> register(@Valid UserDo reqParam) {
        return userService.register(reqParam);
    }
}
