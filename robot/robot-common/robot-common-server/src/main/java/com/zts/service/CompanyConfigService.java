package com.zts.service;

import com.zts.dao.CompanyConfigDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.CompanyConfig;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class CompanyConfigService extends BaseServiceImpl<CompanyConfig,Integer> {

    @Resource
    private CompanyConfigDao companyConfigDao;

    @Override
    public IBaseDao<CompanyConfig, Integer> getBaseDao() {
        return companyConfigDao;
    }

    public String getByVariable(Integer companyId,String variable){

        return companyConfigDao.queryByVariable(companyId,variable);
    }
}
