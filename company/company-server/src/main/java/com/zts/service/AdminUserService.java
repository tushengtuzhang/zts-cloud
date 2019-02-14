package com.zts.service;

import com.zts.dao.AdminUserDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.AdminUser;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class AdminUserService extends BaseServiceImpl<AdminUser,Integer> {

    @Resource
    private AdminUserDao adminUserDao;

    @Override
    public IBaseDao<AdminUser, Integer> getBaseDao() {
        return adminUserDao;
    }
}
