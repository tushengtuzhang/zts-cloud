package com.zts.service;

import com.zts.dao.AppCaseDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.AppCase;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class AppCaseService extends BaseServiceImpl<AppCase, Integer> {

    @Resource
    private AppCaseDao appCaseDao;

    @Override
    public IBaseDao<AppCase, Integer> getBaseDao() {
        return appCaseDao;
    }

}
