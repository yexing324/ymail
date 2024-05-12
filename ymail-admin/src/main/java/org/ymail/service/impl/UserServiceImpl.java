package org.ymail.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysql.cj.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.ymail.entity.UserDo;
import org.ymail.entity.Vo.UserVo;
import org.ymail.filter.UserContext;
import org.ymail.mapper.UserMapper;
import org.ymail.resp.UserResp;
import org.ymail.service.UserService;
import org.ymail.util.CodeContain;
import org.ymail.util.Regex;
import org.ymail.util.Result;
import org.ymail.util.ThreadPool;

import java.util.List;
import java.util.Random;
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
    public Result<Void> register(UserVo reqParam) {
        //校验手机号

        if (CodeContain.getCode(reqParam.getPhone()) == null) {
            return Result.failure("验证码已过期");
        }
        if (!reqParam.getCode().equals(CodeContain.getCode(reqParam.getPhone()))) {
            return Result.failure("验证码错误");
        }
        //正则校验邮箱地址
        if (!Regex.isEmail(reqParam.getMail())) {
            return Result.failure("邮箱地址不合法");
        }

        boolean contains = registerBloomFilter.contains(reqParam.getMail());
        if (contains) {
            return Result.failure("邮箱地址已经存在");
        }
        //查询数据库判断邮箱地址是否存在
        //布隆过滤器可能出现误判
        UserDo ifUser = userMapper.selectOne(new LambdaQueryWrapper<UserDo>().eq(UserDo::getMail, reqParam.getMail()));
        if (ifUser != null) {
            return Result.failure("邮箱地址已经存在");
        }


        //加锁
        RLock lock = redissonClient.getLock(USER_REGISTER + reqParam.getMail());
        try {
            if (lock.tryLock()) {

                UserDo userDo = BeanUtil.copyProperties(reqParam, UserDo.class);

                int insert = userMapper.insert(userDo);
                if (insert > 0) {
                    //将邮箱地址添加到布隆过滤器里面
                    registerBloomFilter.add(reqParam.getMail());
                    return Result.success();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 获得用户登录时的具体信息
     *
     * @return 信息
     */
    @Override
    public Result<UserDo> getUserInfo() {
        Random random = new Random();
        String userMail = UserContext.getUserMail();
        //从数据库查
        UserDo userDo = userMapper.selectOne(new LambdaQueryWrapper<UserDo>().eq(UserDo::getMail, userMail));
        boolean flag = false;
        if (userDo.getNickname() == null) {
            flag = true;
            userDo.setNickname(CodeContain.nickNameList.get(random.nextInt(10)));
        }
        if (userDo.getAvatarName() == null) {
            flag = true;
            userDo.setAvatarName("default.png");
        }
        if (flag) {
            userMapper.updateById(userDo);
        }
        userDo.setAvatarName(CodeContain.uploadAddress+userDo.getAvatarName());
        return Result.success(userDo);


    }


}
