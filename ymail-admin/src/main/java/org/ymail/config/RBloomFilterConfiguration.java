package org.ymail.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置
 */
@Configuration
public class RBloomFilterConfiguration {

    /**
     * 防止注册时查询数据库的布隆过滤器
     */
    @Bean
    public RBloomFilter<String> registerBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter("register");
        cachePenetrationBloomFilter.tryInit(100000, 0.050);
        return cachePenetrationBloomFilter;
    }
}