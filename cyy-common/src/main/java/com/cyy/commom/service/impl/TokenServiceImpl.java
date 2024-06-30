package com.cyy.commom.service.impl;


import com.cyy.commom.config.SecurityConfig;
import com.cyy.commom.constant.CommonConstant;
import com.cyy.commom.dto.BaseUserInfoDTO;
import com.cyy.commom.dto.TokenResponse;
import com.cyy.commom.exception.BizException;
import com.cyy.commom.exception.LoginException;
import com.cyy.commom.service.TokenService;
import com.cyy.commom.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty(prefix = "sys",name = "enable-my-security",havingValue = "true")
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl<T extends BaseUserInfoDTO> implements TokenService<T> {
    ThreadLocal<T> userThreadLocal;
    final RedisTemplate<String, T> redisTemplate;
    final SecurityConfig securityConfig;

    /**
     * 是否刷新token（用于操作接口时，是否给token续命）
     *
     * @param tokenResponse
     * @return
     */
    @Override
    public boolean isRefreshToken(TokenResponse tokenResponse) {
        if (Objects.isNull(tokenResponse)) {
            throw new BizException("token无效或已过期");
        }
        //求token还剩多久过期
        long expireTime = tokenResponse.getExpireDateTime() - DateUtil.getSecondTimeStamp();
        if (expireTime <= 0) {
            throw new BizException("token过期");
        }

        //求token已经使用了多长时间
        long useTime = tokenResponse.getExpire() - expireTime;
        //token使用时间超过三分之一则刷新
        if (useTime > (tokenResponse.getExpire() / 3)) {
            return true;
        }
        return false;
    }

    /**
     * 1、检验token
     * 2、根据规则判断是否要刷新token
     *
     * @param token
     * @return
     */
    @Override
    public T checkToken(String token) {
        if (Strings.isBlank(token)) {
            throw new BizException("token非法");
        }
        T userInfo = redisTemplate.opsForValue().get(CommonConstant.TOKEN_CACHE_KEY + token);
        if (Objects.isNull(userInfo)) {
            throw new BizException("token不存在或已过期");
        }
        boolean isRefresh = isRefreshToken(userInfo.getToken());
        if (isRefresh) {
            userInfo.getToken().setExpireDateTime(DateUtil.getExpireTimeStamp(userInfo.getToken().getExpire(), TimeUnit.DAYS));
            redisTemplate.opsForValue().set(CommonConstant.TOKEN_CACHE_KEY + userInfo.getToken().getToken(), userInfo, userInfo.getToken().getExpire(), userInfo.getToken().getTimeUnit());
        }
        return userInfo;
    }

    /**
     * 设置token
     *
     * @param userInfo
     */
    @Override
    public T setToken(T userInfo) {
        if (userInfo == null) {
            throw new LoginException("用户信息不能为空");
        }
        // 创建一个新的TokenResponse对象，用于存储生成的token及相关信息
        TokenResponse tokenResponse = new TokenResponse();

        // 生成一个唯一的token，使用UUID随机生成字符串
        tokenResponse.setToken(UUID.randomUUID().toString());

        // 设置token的过期时间，从securityConfig中获取配置的过期时间
        tokenResponse.setExpire(securityConfig.getExpire());

        // 计算token的过期时间戳，单位为天
        tokenResponse.setExpireDateTime(DateUtil.getExpireTimeStamp(tokenResponse.getExpire(), TimeUnit.DAYS));

        // 设置token的时间单位为天
        tokenResponse.setTimeUnit(TimeUnit.DAYS);

        // 从Redis缓存中获取以token为键的值，检查该token是否已存在
        Object tokenByCache = redisTemplate.opsForValue().get(CommonConstant.TOKEN_CACHE_KEY + tokenResponse.getToken());

        // 如果缓存中存在相同的token，则抛出异常，表示token已存在，不能登录或注册
        if (Objects.nonNull(tokenByCache)) {
            throw new BizException("token已存在，不能登录或注册");
        }

        // 将token信息设置到userInfo对象中
        userInfo.setToken(tokenResponse);

        // 将userInfo对象存储到Redis缓存中，键为TOKEN_CACHE_KEY + token，过期时间为token的过期时间
        redisTemplate.opsForValue().set(CommonConstant.TOKEN_CACHE_KEY + tokenResponse.getToken(), userInfo, userInfo.getToken().getExpire(), TimeUnit.SECONDS);

        // 返回更新后的userInfo对象
        return userInfo;

    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public T getThreadLocalUser() {
        if (userThreadLocal == null) {
            return null;
        }
        return userThreadLocal.get();
    }

    /**
     * 设置用户信息
     *
     * @param userInfo
     */
    @Override
    public void setThreadLocalUser(T userInfo) {
        if (Objects.isNull(userThreadLocal)) {
            userThreadLocal = new ThreadLocal<>();
        }
        userThreadLocal.set(userInfo);
    }

    /**
     * 删除本地用户
     */
    @Override
    public void removeThreadLocalUser() {
        if (Objects.nonNull(userThreadLocal)) {
            userThreadLocal.remove();
        }
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public T getRedisUser(String token) {
        T object = redisTemplate.opsForValue().get(CommonConstant.USER_CACHE_KEY + token);
        if (Objects.nonNull(object)) {
            return object;
        }
        throw new LoginException("获取用户信息异常");
    }

    /**
     * 获取用户id
     *
     * @return
     */
    @Override
    public Long getThreadLocalUserId() {
        T user = getThreadLocalUser();
        if (Objects.nonNull(user) && user.getId() != null && user.getId() > 0) {
            return user.getId();
        }
        throw new LoginException("获取用户id异常");
    }

    /**
     * 获取租户id
     *
     * @return
     */
    @Override
    public Long getThreadLocalTenantId() {
        T user = getThreadLocalUser();
        if (Objects.nonNull(user) && user.getTenantId() != null && user.getTenantId() > 0) {
            return user.getTenantId();
        }
        throw new LoginException("获取租户id异常");
    }

    /**
     * 清除token
     *
     * @return
     */
    @Override
    public void clearToken() {
        T user = getThreadLocalUser();
        if (user == null || user.getToken() == null
                || Strings.isBlank(user.getToken().getToken())) {
            return;
        }
        String key = CommonConstant.TOKEN_CACHE_KEY + user.getToken().getToken();
        redisTemplate.delete(key);
        log.info("删除token：{}成功，用户id：{}", user.getToken().getToken(), user.getId());
    }
}
