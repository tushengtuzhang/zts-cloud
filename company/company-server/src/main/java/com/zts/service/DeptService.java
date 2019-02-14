package com.zts.service;

import com.zts.dao.DeptDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.Dept;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class DeptService {

    @Resource
    private DeptDao deptDao;

    public Dept find(String id) {
        return deptDao.getOne(id);
    }

    public void save(Dept dept){
        deptDao.save(dept);
    }

}
