package com.zts.service;

import com.zts.dao.FlowQuestionDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.FlowQuestion;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class FlowQuestionService extends BaseServiceImpl<FlowQuestion,Integer> {

    @Resource
    private FlowQuestionDao flowQuestionDao;

    @Override
    public IBaseDao<FlowQuestion, Integer> getBaseDao() {
        return flowQuestionDao;
    }
}
