package com.zts.service.match.reply.tipimpl;

import com.zts.entity.AppCaseQuestion;
import com.zts.feign.RobotCommonFeign;
import com.zts.service.match.reply.ReplyTipHandlerPlugin;
import com.zts.vo.CaseMatchVO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 默认的场景场景回复关联问题tip插件
 * @author zhangtusheng
 */
@Component
@Order(value = 1)
public class ReplyTipRelationQuestionPlugin implements ReplyTipHandlerPlugin {

    @Resource
    private RobotCommonFeign commonFeign;

    /**
     * 获取tips,
     *
     * @param caseMatchVO 场景匹配vo,已经成功匹配到reply
     * @param originTips  默认预先处理过的tips列表
     * @return
     */
    @Override
    public List<String> getTips(CaseMatchVO caseMatchVO, List<String> originTips) {
        List<String> tips = Optional.ofNullable(originTips).orElse(new ArrayList<>());

        //添加关联场景
        List<AppCaseQuestion> relationQuestionList = caseMatchVO.getRelationQuestionList();
        if (relationQuestionList != null && relationQuestionList.size() > 0) {
            relationQuestionList.forEach(relationQuestion -> tips.add(relationQuestion.getQuestion()));
        } else {
            /*GuessedQuestion guestQuestion = new GuessedQuestion();
            guestQuestion.setCompany(caseMatchVO.getAppCase().getCompany());
            guestQuestion.setCurrentId(String.valueOf(caseMatchVO.getAppCase().getId()));
            guestQuestion.setCurrentType("CASE");

            List<GuessedQuestion> guessedQuestionList = commonFeign.top(guestQuestion);*/

            /*List<String> guessedQuestionStringList = new ArrayList<>();

            if (guessedQuestionList != null && guessedQuestionList.size() > 1) {

                guessedQuestionList.forEach(guessedQuestion -> guessedQuestionStringList.add(guessedQuestion.getNextQ()));
            }

            tips.addAll(guessedQuestionStringList);*/
        }

        return tips;
    }

}
