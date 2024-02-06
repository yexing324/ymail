package org.ymail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ymail.entity.UserDo;
import org.ymail.service.UserService;
import org.ymail.util.Result;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ymail/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public Result<String> login(@Validated(UserDo.login.class) @RequestBody UserDo reParam) {
        return userService.login(reParam);
    }

    @PostMapping("/register")
    public Result<Void> register(@Validated(UserDo.Register.class) @RequestBody UserDo reqParam) {
        return userService.register(reqParam);
    }
}
