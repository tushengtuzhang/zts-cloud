package com.zts.service;

import com.zts.dao.CompanyDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.Company;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class CompanyService extends BaseServiceImpl<Company, Long> {

    @Resource
    private CompanyDao companyDao;

    @Override
    public IBaseDao<Company, Long> getBaseDao() {
        return companyDao;
    }
}
