package com.zts.service.match.action;

import com.zts.entity.AppCaseAction;
import com.zts.vo.ConditionValue;
import com.zts.vo.ReplyMatchCondition;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangtusheng
 */
@Service
public class ActionHandlerService {

    private List<String> removeDuplicate(List<String> list) {
        Set<String> hashSet = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(hashSet);
        return list;
    }

    public void replyMatchConditionMatch(AppCaseAction appCaseAction, List<String> bubbleList, List<ReplyMatchCondition> replyMatchConditionList) {
        for (ReplyMatchCondition replyMatchCondition : replyMatchConditionList) {

            if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId()) && appCaseAction.getId().equals(replyMatchCondition.getActionId())) {
                if (replyMatchCondition != null && replyMatchCondition.getValueList() != null) {
                    for (ConditionValue conditionValue : replyMatchCondition.getValueList()) {
                        bubbleList.add(conditionValue.getValue());
                    }
                }
            }
        }
    }
}
