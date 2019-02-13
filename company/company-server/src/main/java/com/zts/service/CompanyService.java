package com.zts.service;

import com.zts.dao.CompanyDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.Company;
import com.zts.service.impl.BaseServiceImpl;
import com.zts.util.redis.RedisUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class CompanyService extends BaseServiceImpl<Company, Integer> {

    @Resource
    private CompanyDao companyDao;

    @Override
    public IBaseDao<Company, Integer> getBaseDao() {
        return companyDao;
    }

    @Cacheable(value = "company",key="#id")
    @Override
    public Company find(Integer id) {
        System.out.println("load2");
        return super.find(id);
    }
}
