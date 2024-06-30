package com.cyy.commom.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redisson")
@ConditionalOnProperty("redisson.password")
@Data
public class RedissonProperties {
    // 连接超时时间，默认为3000毫秒
    private int timeout = 3000;

    // Redis服务器地址
    private String address;

    // Redis服务器密码
    private String password;

    // 默认数据库索引，默认为0
    private int database = 0;

    // 连接池大小，默认为64
    private int connectionPoolSize = 64;

    // 连接池最小空闲连接数，默认为10
    private int connectionMinimumIdleSize=10;

    // 从服务器连接池大小，默认为250
    private int slaveConnectionPoolSize = 250;

    // 主服务器连接池大小，默认为250
    private int masterConnectionPoolSize = 250;

    // Sentinel服务器地址数组
    private String[] sentinelAddresses;

    // Redis主服务器名称
    private String masterName;

}