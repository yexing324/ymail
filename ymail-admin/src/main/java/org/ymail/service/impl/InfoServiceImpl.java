package org.ymail.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ymail.filter.UserContext;
import org.ymail.service.InfoService;
import org.ymail.util.Result;

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {
    /**
     * 获得用户登录时的具体信息
     * @return 信息
     */
    @Override
    public Result<Void> getLoginInfo() {

        System.out.println("执行到此");
        System.out.println(UserContext.getUserPhone());
        System.out.println(UserContext.getUserMail());
        return null;
    }
}
