package org.ymail.service.impl;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.ymail.entity.UserDo;
import org.ymail.mapper.UserMapper;
import org.ymail.service.UserService;
import org.ymail.util.Regex;
import org.ymail.util.Result;

import static org.ymail.constant.RedisConstant.USER_REGISTER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RBloomFilter<String> registerBloomFilter;
    private final UserMapper userMapper;
    private final RedissonClient redissonClient;
    @Override
    public Result<Void> register(UserDo reqParam) {
        //验证
        boolean validate = Regex.validate(reqParam.getMail(), "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if(!validate){
            return Result.failure("邮箱地址只能由数字字母和下划线组成");
        }
        //TODO:密码的长度验证

        boolean contains = registerBloomFilter.contains(reqParam.getMail());
        if(contains){
            return Result.failure("邮箱号已经存在");
        }
        //布隆过滤器判断不存在，不存在误判
        // 由于布隆过滤器是使用redis实现，所以不再查询redis
        //TODO:手机号验证
        //
        RLock lock=redissonClient.getLock(USER_REGISTER+reqParam.getMail());
        try {
            if(lock.tryLock()){
                int insert = userMapper.insert(reqParam);
                if(insert>0){
                    //将邮箱地址添加到布隆过滤器里面
                    registerBloomFilter.add(reqParam.getMail());
                    return Result.success();
                }
            }

        }catch (Exception e){
            //重新添加
            registerBloomFilter.add(reqParam.getMail());
            return Result.failure("邮箱地址已经存在");
        }
        return Result.failure("注册失败，请重试");
    }
}
