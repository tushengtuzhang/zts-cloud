package com.zts.service;

import com.zts.dao.CaseQuestionDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.AppCaseQuestion;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class CaseQuestionService extends BaseServiceImpl<AppCaseQuestion,Integer> {

    @Resource
    private CaseQuestionDao caseQuestionDao;

    @Override
    public IBaseDao<AppCaseQuestion, Integer> getBaseDao() {
        return caseQuestionDao;
    }
}
