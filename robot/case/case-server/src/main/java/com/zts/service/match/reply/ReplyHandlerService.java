package com.zts.service.match.reply;

import com.zts.entity.AppCaseAction;
import com.zts.entity.AppCaseReply;
import com.zts.entity.CompCorpus;
import com.zts.entity.CompCorpusLabel;
import com.zts.service.match.constant.CaseConstant;
import com.zts.service.match.constant.CaseMatchLifeCycleConstant;
import com.zts.service.match.redis.CaseMatchRedisService;
import com.zts.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtusheng
 * @date 2018/12/18
 */
@Service
public class ReplyHandlerService {

    @Resource
    private CaseMatchRedisService caseMatchRedisService;


    @Resource
    private ReplyTipHandlerService replyTipHandlerService;

    public void matchReply(String matchValue, AppCaseAction appCaseAction, List<AppCaseReply> appCaseReplyList) {

        //继续匹配的场景回复
        for (AppCaseReply appCaseReply : appCaseReplyList) {

            List<List<ReplyMatchCondition>> replyMatchConditionListCombination = appCaseReply.getReplyMatchConditionListCombination();

            if (replyMatchConditionListCombination != null && replyMatchConditionListCombination.size() > 0) {
                for (List<ReplyMatchCondition> replyMatchConditionList : replyMatchConditionListCombination) {

                    for (ReplyMatchCondition replyMatchCondition : replyMatchConditionList) {
                        boolean match = false;
                        if (appCaseAction.getId().equals(replyMatchCondition.getActionId())) {

                            if (replyMatchCondition.getValueList() != null) {
                                for (ConditionValue conditionValue : replyMatchCondition.getValueList()) {
                                    if (conditionValue == null) {
                                        replyMatchCondition.setMatch2(true);
                                    }

                                    if (conditionValue.getType() != null && replyMatchCondition.getCorpusTypeId() != null && conditionValue.getValue() != null) {
                                        //从临时语料匹配
                                        if (conditionValue.getType() == CaseConstant.ConditionValueType.TEMPORARY.getValue()) {
                                            if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId())) {

                                                if (appCaseAction.getNeedMatch() == 0 || conditionValue.getValue().equals(matchValue)) {

                                                    replyMatchCondition.setMatch2(true);
                                                    break;
                                                }
                                            }
                                        }
                                        //从词库匹配
                                        if (conditionValue.getType() == CaseConstant.ConditionValueType.CORPUS.getValue()) {
                                            if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId())) {

                                                if (appCaseAction.getNeedMatch() == 0 || conditionValue.getValue().equals(matchValue)) {

                                                    replyMatchCondition.setMatch2(true);
                                                    break;
                                                }
                                            }

                                        }
                                        //从标签匹配
                                        if (conditionValue.getType() == CaseConstant.ConditionValueType.LABEL.getValue()) {
                                            if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId()) && conditionValue.getValue().equals(matchValue)) {
                                                replyMatchCondition.setMatch2(true);

                                            } else {

                                                CompCorpusLabel compCorpusLabel = null;//corpusLabelService.selectOneByCorpusTypeIdAndLabelName(replyMatchCondition.getCorpusTypeId(), conditionValue.getValue());
                                                if (compCorpusLabel != null && compCorpusLabel.getId() != null) {
                                                    for (CompCorpus compCorpus : compCorpusLabel.getCompCorpusList()) {
                                                        if (appCaseAction.getNeedMatch() == 0 || compCorpus.getName().equals(matchValue)) {
                                                            match = true;
                                                            replyMatchCondition.setMatch2(true);
                                                            break;
                                                        }
                                                    }
                                                    if (match) {
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                replyMatchCondition.setMatch2(true);
                            }
                        }
                    }
                }
            }
        }
    }


    public void revertActionFromReply(CaseMatchVO caseMatchVO, AppCaseAction appCaseAction, String identification) {

        if (caseMatchVO.getAppCase().getAppCaseReplyList() != null && caseMatchVO.getAppCase().getAppCaseReplyList().size() > 1) {
            caseMatchVO.getAppCase().getAppCaseReplyList().get(0).setMatch(false);
        }

        //继续匹配的场景回复
        for (AppCaseReply appCaseReply : caseMatchVO.getAppCase().getAppCaseReplyList()) {
            List<List<ReplyMatchCondition>> replyMatchConditionListCombination = appCaseReply.getReplyMatchConditionListCombination();
            if (replyMatchConditionListCombination != null && replyMatchConditionListCombination.size() > 0) {
                for (List<ReplyMatchCondition> replyMatchConditionList : replyMatchConditionListCombination) {
                    for (ReplyMatchCondition replyMatchCondition : replyMatchConditionList) {
                        if (appCaseAction.getId().equals(replyMatchCondition.getActionId())) {

                            if (replyMatchCondition.getValueList() != null) {
                                for (ConditionValue conditionValue : replyMatchCondition.getValueList()) {
                                    if (conditionValue.getType() != null && replyMatchCondition.getCorpusTypeId() != null && conditionValue.getValue() != null) {
                                        //从临时语料匹配
                                        if (conditionValue.getType() == CaseConstant.ConditionValueType.TEMPORARY.getValue()) {
                                            if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId())) {
                                                replyMatchCondition.setMatch2(false);
                                                appCaseReply.setMatch(false);
                                                break;
                                            }
                                        }
                                        //从词库匹配
                                        if (conditionValue.getType() == CaseConstant.ConditionValueType.CORPUS.getValue()) {
                                            if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId())) {
                                                replyMatchCondition.setMatch2(false);
                                                appCaseReply.setMatch(false);
                                                break;
                                            }

                                        }
                                        //从标签匹配
                                        if (conditionValue.getType() == CaseConstant.ConditionValueType.LABEL.getValue()) {
                                            if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId())) {
                                                replyMatchCondition.setMatch2(false);
                                                appCaseReply.setMatch(false);
                                                break;

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        caseMatchRedisService.saveCaseMatchVO(identification, caseMatchVO);
    }

    /**
     * 场景回复
     *
     * @param identification identification
     * @param caseMatchVO    caseMatchVO
     * @return CaseReplyMessage
     */
    public CaseReplyMessage reply(String identification, CaseMatchVO caseMatchVO) {

        //带条件回复处理
        CaseReplyMessage caseReplyMessage = replyHandler(caseMatchVO, identification);
        if (caseReplyMessage != null) {
            return caseReplyMessage;
        }

        //如果匹配不到查询条件,返回默认回复
        return defaultReply(caseMatchVO, identification);
    }

    private CaseReplyMessage replyHandler(CaseMatchVO caseMatchVO, String identification) {
        for (AppCaseReply appCaseReply : caseMatchVO.getAppCase().getAppCaseReplyList()) {
            if (appCaseReply.getReplyMatchConditionListCombination() == null || appCaseReply.getReplyMatchConditionListCombination().size() == 0) {
                continue;
            }
            //循环组合
            for (List<ReplyMatchCondition> replyMatchConditionList : appCaseReply.getReplyMatchConditionListCombination()) {
                boolean match = true;
                for (ReplyMatchCondition replyMatchCondition : replyMatchConditionList) {
                    //如果不匹配
                    if (!replyMatchCondition.isMatch2()) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    List<Action> actions = new ArrayList<>();
                    //多条件组
                    List<List<ReplyMatchCondition>> replyMatchConditionListCombination = appCaseReply.getReplyMatchConditionListCombination();
                    //一个条件组合
                    for (List<ReplyMatchCondition> replyMatchConditionList2 : replyMatchConditionListCombination) {
                        //一个动作对应的条件
                        for (ReplyMatchCondition replyMatchCondition : replyMatchConditionList2) {
                            //和动作做匹配
                            for (AppCaseAction appCaseAction : caseMatchVO.getAppCase().getAppCaseActionList()) {

                                if (appCaseAction.getCompCorpusType().getId().equals(replyMatchCondition.getCorpusTypeId()) && appCaseAction.getId().equals(replyMatchCondition.getActionId())) {
                                    //受限的场景，不添加
                                    if (!appCaseAction.isBounded()) {
                                        Action action = new Action(appCaseAction.getId(), appCaseAction.getValue(), appCaseAction.getMatchValue());
                                        if (!actions.contains(action)) {
                                            actions.add(action);
                                        }
                                        break;
                                    }

                                }
                            }
                        }
                    }

                    appCaseReply.setMatch(true);
                    String reply = appCaseReply.getReply();

                    //直接替换，不在前端进行触发处理
                    List<String> actionKeyList = new ArrayList<>();
                    for (Action action : actions) {
                        actionKeyList.add(action.getKey());
                    }
                    for (Map.Entry<String, String> entry : caseMatchVO.getMatchedActionMap().entrySet()) {
                        if (!actionKeyList.contains(entry.getKey())) {
                            String key = String.format("\\%s\\%s", entry.getKey().substring(0, 1), entry.getKey().substring(1));
                            reply = reply.replaceAll(key, entry.getValue());
                        }
                    }

                    caseMatchRedisService.resetCaseMatchVO(identification);

                    List<String> tips = getReplyTips(caseMatchVO);


                    //添加文件实体
                    CaseReplyMessage.Ref ref = ref(caseMatchVO.getAppCase().getFileBaseId());

                    if (reply == null) {
                        reply = "";
                    }
                    return new CaseReplyMessage(caseMatchVO.getAppCase().getId(), appCaseReply.getId(), CaseMatchLifeCycleConstant.FINISHED, ref, caseMatchVO.getAppCaseFirstQuestion(), actions, reply, tips, caseMatchVO.getMatchedId());
                }
            }
        }

        return null;
    }

    private CaseReplyMessage defaultReply(CaseMatchVO caseMatchVO, String identification) {
        AppCaseReply defaultAppCaseReply = null;
        for (AppCaseReply appCaseReply : caseMatchVO.getAppCase().getAppCaseReplyList()) {
            if (appCaseReply.getReplyMatchConditionListCombination() == null || appCaseReply.getReplyMatchConditionListCombination().size() == 0) {
                defaultAppCaseReply = appCaseReply;
                break;
            }
        }
        //如果匹配不到查询条件
        if (defaultAppCaseReply != null) {
            String reply = defaultAppCaseReply.getReply();
            caseMatchRedisService.resetCaseMatchVO(identification);


            //添加场景动作可选项
            List<Action> actions = new ArrayList<>();
            for (AppCaseAction appCaseAction : caseMatchVO.getAppCase().getAppCaseActionList()) {
                if (!appCaseAction.isBounded()) {
                    Action action = new Action(appCaseAction.getId(), appCaseAction.getValue(), appCaseAction.getMatchValue());
                    actions.add(action);
                }

            }

            List<String> actionKeyList = new ArrayList<>();
            for (Action action : actions) {
                actionKeyList.add(action.getKey());
            }
            for (Map.Entry<String, String> entry : caseMatchVO.getMatchedActionMap().entrySet()) {
                if (!actionKeyList.contains(entry.getKey())) {
                    String key = String.format("\\%s\\%s", entry.getKey().substring(0, 1), entry.getKey().substring(1));
                    reply = reply.replaceAll(key, entry.getValue());
                }
            }
            caseMatchRedisService.resetCaseMatchVO(identification);

            List<String> tips = getReplyTips(caseMatchVO);

            //文件实体
            CaseReplyMessage.Ref ref = ref(caseMatchVO.getAppCase().getFileBaseId());
            if (reply == null) {
                reply = "";
            }
            return new CaseReplyMessage(caseMatchVO.getAppCase().getId(), defaultAppCaseReply.getId(), CaseMatchLifeCycleConstant.FINISHED, ref, caseMatchVO.getAppCaseFirstQuestion(), actions, reply, tips, caseMatchVO.getMatchedId());
        } else {
            caseMatchRedisService.resetCaseMatchVO(identification);
            return null;
        }
    }

    public CaseReplyMessage.Ref ref(Integer fileBaseId) {
        CaseReplyMessage.Ref ref = new CaseReplyMessage().new Ref();
        ref=null;
        //转换成ref给到mobile
        /*CaseReplyMessage.Ref ref = new CaseReplyMessage().new Ref();
        if (fileBaseId != null) {
            FileBase fileBase = fileBaseService.(fileBaseId);
            if (fileBase != null) {
                ref.setRefText(fileBase.getFileName());
                ref.setRefId(fileBase.getFileId());
                ref.setRefName(fileBase.getFileName());
            }
        } else {
            ref = null;
        }*/

        return ref;
    }

    private List<String> getReplyTips(CaseMatchVO caseMatchVO) {
        return replyTipHandlerService.getTips(caseMatchVO);
    }


}
