package com.zts.service.match.cases;

import com.zts.entity.AppCase;
import com.zts.entity.AppCaseAction;
import com.zts.entity.AppCaseReply;
import com.zts.service.AppCaseService;
import com.zts.service.match.action.ActionHandlerService;
import com.zts.service.match.constant.ReplyTypeConstant;
import com.zts.service.match.redis.CaseMatchRedisService;
import com.zts.service.match.redis.ThirdPartyServiceRedisService;
import com.zts.vo.CaseMatchVO;
import com.zts.vo.ReplyMatchCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangtusheng
 */
@Service
public class CaseMatchVOHandlerService {
    @Resource
    private AppCaseService appCaseService;

    @Resource
    private CaseMatchRedisService caseMatchRedisService;

    @Resource
    private ActionHandlerService actionHandlerService;

    @Resource
    private ThirdPartyServiceRedisService thirdPartyServiceRedisService;

    /**
     * 判断caseMatchVO是否全匹配了
     * @param identification
     * @return
     */
    public boolean matchedAllAction(String identification){
        boolean match=true;
        CaseMatchVO caseMatchVO=caseMatchRedisService.getCaseMatchVO(identification);
        if(caseMatchVO!=null&&caseMatchVO.getAppCase().getAppCaseActionList().size()>0){
            for(AppCaseAction appCaseAction:caseMatchVO.getAppCase().getAppCaseActionList()){
                if(!appCaseAction.isMatch()){
                    match=false;
                    break;
                }
            }
        }

        return match;
    }


    public CaseMatchVO initCaseMatchVO(CaseMatchVO caseMatchVO, String request, String identification){
        //场景
        AppCase appCase = caseMatchVO.getAppCase();
        if (appCase == null) {
            appCase = appCaseService.find(caseMatchVO.getAppCaseQuestion().getAppCase().getId());
            caseMatchVO.setAppCase(appCase);
        }

        //动作可选列表
        if(appCase.getAppCaseActionList().size()>0){
            for (AppCaseAction appCaseAction : appCase.getAppCaseActionList()) {
                List<String> tips = new ArrayList<>();
                //如果动作是受限的，则从画像中取值
                if(appCaseAction.isBounded()){
                    /*List<Persona> personaList = personaService.selectListByBounded(caseMatchVO.getAppCase().getCompany().getId(), identification, appCaseAction.getCorpusTypeId(), appCaseAction.getName(), true);
                    if(personaList!=null&&personaList.size()>0){
                        for(Persona persona:personaList){
                            tips.add(persona.getLabelValue());
                        }
                    }*/
                }else{
                    //普通场景
                    if(appCase.getReplyType().equals(ReplyTypeConstant.NORMAL_REPLY)){
                        for (AppCaseReply appCaseReply : appCase.getAppCaseReplyList()) {
                            List<List<ReplyMatchCondition>> replyMatchConditionListCombination = appCaseReply.getReplyMatchConditionListCombination();
                            if (replyMatchConditionListCombination != null && replyMatchConditionListCombination.size() > 0) {
                                for (List<ReplyMatchCondition> replyMatchConditionList : replyMatchConditionListCombination) {
                                    actionHandlerService.replyMatchConditionMatch(appCaseAction, tips, replyMatchConditionList);
                                }
                            }
                        }

                        tips=removeDuplicate(tips);
                        if (!tips.contains("其他")&&appCaseAction.getNeedMatch()==1) {
                            tips.add("其他");
                        }
                    //第三方服务场景
                    }else if(appCase.getReplyType().equals(ReplyTypeConstant.THIRD_PARTY_SERVICE_REPLY)){
                        /*for (ThirdPartyServiceComponent component : mThirdPartyServiceComponents) {
                            if (component.matchService(appCase.getOriginalServiceId())) {
                                tips=component.bubbleList(appCaseAction);
                            }
                        }*/
                    //公司服务场景（给什么呢）待做
                    }else{

                    }
                }
                appCaseAction.setBubbleList(tips);
            }
        }

        caseMatchVO.setAsk(request);

        caseMatchRedisService.saveCaseMatchVO(identification,caseMatchVO);

        //初始化第三方服务VO对象
        if(caseMatchVO.getAppCase().getOriginalServiceId()!=null&&caseMatchVO.getAppCase().getReplyType().equals(ReplyTypeConstant.THIRD_PARTY_SERVICE_REPLY)){
            thirdPartyServiceRedisService.initThirdPartyServiceVO(appCase.getCompany().getId(),identification,appCase.getOriginalServiceId());
        }else if(caseMatchVO.getAppCase().getCompanyServiceId()!=null&&caseMatchVO.getAppCase().getReplyType().equals(ReplyTypeConstant.COMPANY_SERVICE_REPLY)){
            thirdPartyServiceRedisService.initThirdPartyCompanyServiceVO(appCase.getCompany().getId(),identification,appCase.getCompanyServiceId());
        }


        return caseMatchVO;
    }

    private List<String> removeDuplicate(List<String> list) {
        Set<String> hashSet = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(hashSet);
        return list;
    }

}
