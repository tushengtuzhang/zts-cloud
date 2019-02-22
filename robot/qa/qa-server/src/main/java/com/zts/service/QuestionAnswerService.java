package com.zts.service;

import com.zts.dao.IBaseDao;
import com.zts.dao.QuestionAnswerDao;
import com.zts.entity.QuestionAnswer;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class QuestionAnswerService extends BaseServiceImpl<QuestionAnswer,Integer> {

    @Resource
    private QuestionAnswerDao questionAnswerDao;

    @Override
    public IBaseDao<QuestionAnswer, Integer> getBaseDao() {
        return questionAnswerDao;
    }
}
