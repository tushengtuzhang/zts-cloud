package com.zts.dao;

import com.zts.entity.CompanyUser;
import com.zts.util.redis.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 公司用户 redis dao
 */
@Component
public class CompanyUserRedisDao {
    @Resource
    private RedisUtils redisUtils;

    public CompanyUser getByIdentification(String identification) {
        return (CompanyUser) redisUtils.get(getKey(identification));
    }

    public void save(CompanyUser user) {
        //保存60分钟
        redisUtils.save(getKey(user.getIdentification()), user, 60, TimeUnit.MINUTES);
    }

    private String getKey(String identification) {
        return "CompanyUser_" + identification;
    }


    /**
     * 设置匿名用户生存时间
     *
     * @param userIdentification
     * @param timeout            设置生存时间 ,单位分钟
     */
    public void setAvailableTime(String userIdentification, long timeout) {
        String key = getKey(userIdentification);
        redisUtils.expire(key, timeout, TimeUnit.MINUTES);
    }

}