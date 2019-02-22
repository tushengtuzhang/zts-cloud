package com.zts.service.match;


import com.zts.entity.QuestionAnswer;
import com.zts.feign.RobotCommonFeign;
import com.zts.feign.SimilarityFeign;
import com.zts.service.QuestionAnswerService;
import com.zts.vo.Message;
import com.zts.vo.QuestionAnswerMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Service
public class QuestionAnswerMatchService {

    @Resource
    private SimilarityFeign similarityFeign;

    @Resource
    private RobotCommonFeign robotCommonFeign;

    @Resource
    private QuestionAnswerService questionAnswerService;


    /**快问快答默认匹配阈值*/
     static final Double QUESTION_ANSWER_MATCH_MAX_SCORE=0.9d;

    public Message response(Integer companyId, String request, String identification){

        QuestionAnswer questionAnswer=this.match(companyId,request);

        Double qaMatchMaxScore = QUESTION_ANSWER_MATCH_MAX_SCORE;
        //TODO 查询常量
        String questionAnswerMatchValue=robotCommonFeign.getByVariable(companyId,"QAMatch");
        if(questionAnswerMatchValue!=null&&!"".equals(questionAnswerMatchValue)){
            qaMatchMaxScore=Double.valueOf(questionAnswerMatchValue);
        }

        if(questionAnswer!=null&&questionAnswer.getScore()!=null&&questionAnswer.getScore()>qaMatchMaxScore){


            //TODO 保存日志
            Integer matchedLogId = robotCommonFeign.saveMatchedLog(companyId,identification,request, "FAQ",questionAnswer.getId().toString(),questionAnswer.getBestMatchQuestion());

            //TODO 添加热门关联
            List<String> bubbleList= null;

            return new QuestionAnswerMessage(questionAnswer.getId(), questionAnswer.getQuestion().split("\n")[0],questionAnswerService.find(questionAnswer.getId()).getAnswer(),bubbleList,matchedLogId);
        }

        return null;
    }


    public QuestionAnswer match(Integer companyId, String request){

        return similarityFeign.getBestMatchQA(companyId,request);

    }

}
