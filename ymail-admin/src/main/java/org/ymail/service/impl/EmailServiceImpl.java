package org.ymail.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;
import org.ymail.database.BaseDO;
import org.ymail.entity.Email;
import org.ymail.filter.UserContext;
import org.ymail.mapper.EmailMapper;
import org.ymail.service.EmailService;
import org.ymail.util.Result;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailMapper emailMapper;
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

    @Override
    public Result<Object> getMessage() {
        LambdaQueryWrapper<Email>queryWrapper=new LambdaQueryWrapper<Email>()
                .eq(Email::getTo,UserContext.getUserMail())
                .eq(Email::getDelFlag,0)
                .orderByDesc(Email::getUpdateTime);

        List<Email> emails = emailMapper.selectList(queryWrapper);
        return Result.success(emails);
    }
}
