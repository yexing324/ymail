package org.ymail.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.ymail.entity.UserDo;
import org.ymail.mapper.UserMapper;
import org.ymail.resp.UserResp;
import org.ymail.service.UserService;
import org.ymail.util.Regex;
import org.ymail.util.Result;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.ymail.constant.RedisConstant.USER_REGISTER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RBloomFilter<String> registerBloomFilter;
    private final UserMapper userMapper;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<Void> register(UserDo reqParam) {
        //验证
        boolean validate = Regex.validate(reqParam.getMail(), "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if (!validate) {
            return Result.failure("邮箱地址只能由数字字母和下划线组成");
        }
        //TODO:密码的长度验证

        boolean contains = registerBloomFilter.contains(reqParam.getMail());
        if (contains) {
            return Result.failure("邮箱号已经存在");
        }
        //布隆过滤器判断不存在，不存在误判
        // 由于布隆过滤器是使用redis实现，所以不再查询redis
        //TODO:手机号验证
        //
        RLock lock = redissonClient.getLock(USER_REGISTER + reqParam.getMail());
        try {
            if (lock.tryLock()) {
                int insert = userMapper.insert(reqParam);
                if (insert > 0) {
                    //将邮箱地址添加到布隆过滤器里面
                    registerBloomFilter.add(reqParam.getMail());
                    return Result.success();
                }
            }

        } catch (Exception e) {
            //重新添加
            registerBloomFilter.add(reqParam.getMail());
            return Result.failure("邮箱地址已经存在");
        }
        return Result.failure("注册失败，请重试");
    }

    @Override
    public Result<UserResp> login(UserDo reParam) {
        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDo::getPassword, reParam.getPassword());
        queryWrapper.eq(UserDo::getDelFlag, 0);
        UserDo userDo;
        if (reParam.getMail().contains("@")) {
            queryWrapper.eq(UserDo::getMail, reParam.getMail());
            userDo = userMapper.selectOne(queryWrapper);
        } else {
            queryWrapper.eq(UserDo::getPhone, reParam.getMail());
            queryWrapper.orderByDesc(UserDo::getCreateTime);
            List<UserDo> list = userMapper.selectList(queryWrapper);
            userDo = list.isEmpty() ? null : list.get(0);
        }
        //查询数据库
        if (userDo == null) {
            throw new RuntimeException("账号或密码错误");
        }
        //查询redis
        Boolean flag = stringRedisTemplate.hasKey("login_account:" + userDo.getMail());
        if (Boolean.TRUE.equals(flag)) {//redis中已经包含该用户
            Set<Object> keys = stringRedisTemplate.opsForHash().keys("login_account:" + userDo.getMail());
            UserResp userResp = UserResp.builder().mail(userDo.getMail()).cookie(keys.iterator().next().toString()).build();
            return Result.success(userResp);
        }
        //不包含
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put("login_account:" + userDo.getMail(), token, JSON.toJSONString(userDo));
        stringRedisTemplate.expire("login_account:" + userDo.getMail(), 30L, TimeUnit.DAYS);
        UserResp userResp = UserResp.builder().mail(userDo.getMail()).cookie(token).build();
        return Result.success(userResp);
    }


}
