package org.ymail.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * springboot3.0和rocketmq不兼容
 * 导入配置类
 */
@Configuration
@Import({RocketMQAutoConfiguration.class})
public class RocketMQConfig {
}