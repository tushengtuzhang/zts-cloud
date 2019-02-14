package com.zts.service;

import com.zts.dao.AdminRoleDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.AdminRole;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class AdminRoleService extends BaseServiceImpl<AdminRole,Integer> {

    @Resource
    private AdminRoleDao adminRoleDao;

    @Override
    public IBaseDao<AdminRole, Integer> getBaseDao() {
        return adminRoleDao;
    }
}
