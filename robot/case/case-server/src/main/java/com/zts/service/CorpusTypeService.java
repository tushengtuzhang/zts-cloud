package com.zts.service;

import com.zts.dao.CorpusTypeDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.CorpusType;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class CorpusTypeService extends BaseServiceImpl<CorpusType,Integer> {

    @Resource
    private CorpusTypeDao corpusTypeDao;

    @Override
    public IBaseDao<CorpusType, Integer> getBaseDao() {
        return corpusTypeDao;
    }
}
