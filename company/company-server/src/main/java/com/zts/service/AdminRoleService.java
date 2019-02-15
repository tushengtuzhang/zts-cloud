package com.zts.service;

import com.zts.dao.AdminRoleDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.AdminRole;
import com.zts.entity.AdminUser;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Service
public class AdminRoleService extends BaseServiceImpl<AdminRole,Integer> {

    @Resource
    private AdminRoleDao adminRoleDao;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private DeptService deptService;

    @Override
    public IBaseDao<AdminRole, Integer> getBaseDao() {
        return adminRoleDao;
    }


}
