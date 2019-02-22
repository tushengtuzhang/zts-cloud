package com.zts.service.match;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhangtusheng
 */
@Service
@Slf4j
public class MatchProcessService {

    /*@Resource
    private QuestionAnswerMatchService questionAnswerMatchService;

    @Resource
    private FlowMatchService flowMatchService;
    @Resource
    private CaseMatchService caseMatchService;
    @Resource
    private CaseMatchVOHandlerService caseMatchVOHandlerService;

    @Resource
    private ServiceMatchService serviceMatchService;

    @Resource
    private AppCaseService appCaseService;

    @Resource
    private FlowService flowService;

    @Resource
    private CompanyConfigService companyConfigService;

    @Resource
    private MatchRedisService matchRedisService;

    @Resource
    private MatchedLogHandlerService matchedLogHandlerService;

    @Resource
    private FlowQuestionAnswerService flowQuestionAnswerService;

    @Resource
    private CompanyFeign companyFeign;

    @Value("${otalk.internal-deployed:false}")
    private boolean isInternalDeployed;

    @Resource
    private CaseHandlerService caseHandlerService;*/

    /**
     * 业务逻辑处理（场景、流程树、第三方服务场景）
     * @param companyId 公司Id
     * @param request   请求
     * @param identification companyUser的identification
     * @param isAnonymous 是否匿名
     * @return Message
     */
    /*public Message response(Integer companyId, String request, String identification, boolean isAnonymous){

        try{
            String companyName=companyFeign.get(companyId).getName();
            String userName=companyFeign.getUserNameByIdentification(identification);

            //redis 获取匹配场景
            CaseMatchVO caseMatchVO = matchRedisService.getCaseMatchVO(identification);
            if (caseMatchVO == null) {

                caseMatchVO = new CaseMatchVO();
                AppCaseVO appCaseVO=new AppCaseVO();

                //通过流程树点击进去，匹配快问快答(即直接出快问快答)
                Message message=matchFlowQuestionAnswer(companyId,identification,request,isAnonymous);
                if(message!=null){
                    return message;
                }

                //通过流程树点击进去，匹配场景
                appCaseVO=matchFlowAppCaseVO(appCaseVO,companyId,identification,request,isAnonymous);

                if(appCaseVO==null||appCaseVO.getAppCase()==null){
                    appCaseVO = caseHandlerService.match(companyId, request);
                }

                Double caseMatchMaxScore=companyConfigService.getCaseMatchMaxScore(companyId);
                double maxScore = appCaseVO.getScore();

                if (maxScore > caseMatchMaxScore) {
                    message=matchHandler(maxScore,caseMatchVO,appCaseVO,companyId,companyName,identification,userName,request,isAnonymous);
                    if(message!=null){
                        return message;
                    }
                }
                //不匹配处理
                return noMatchHandler(companyId,identification,request,isAnonymous);
            }

            caseMatchVO.setAsk(request);
            matchRedisService.saveCaseMatchVO(identification,caseMatchVO);

            //后续匹配动作的
            caseMatchVO=matchRedisService.getCaseMatchVO(identification);

            if(caseMatchVO==null){
                matchRedisService.deleteLastCase(identification);
                return this.response(companyId,request,identification,isAnonymous);
            }
            caseMatchVO=matchRedisService.getCaseMatchVO(identification);
            if (caseMatchVO.getAppCase().getReplyType().equals(ReplyTypeConstant.NORMAL_REPLY)) {
                return caseMatchService.nextProcess(caseMatchVO, caseMatchVO.getAppCase().getCompanyId(), identification, request,isAnonymous);
            } else {
                return serviceMatchService.nextProcess(caseMatchVO, caseMatchVO.getAppCase().getCompanyId(), identification, request,isAnonymous);
            }

        }catch(Exception e){
            log.error(e.getMessage(),e);
            matchRedisService.resetCaseMatchVO(identification);
            return new ErrorMessage("网络开小差，请稍候重试");
        }
    }

    private Message matchHandler(Double maxScore,CaseMatchVO caseMatchVO,AppCaseVO appCaseVO,Integer companyId,String companyName,String identification,String userName,String request,boolean isAnonymous){
        //不完全匹配，找流程树看能不能找到匹配的，如果匹配，优先出流程树
        Double zeroPointNineNine=0.99d;
        if (maxScore <zeroPointNineNine) {
            Message message = flowMatchService.process(companyId, request, identification,isAnonymous);
            if (message != null) {
                return message;
            }
            matchRedisService.resetCaseMatchVO(identification);
        }

        QuestionAnswer questionAnswer = questionAnswerMatchService.match(companyId, request);
        if(questionAnswer.getScore()>maxScore){
            return questionAnswerMatchService.response(companyId,request,identification,isAnonymous);
        }

        caseMatchVO.setAppCaseQuestion(appCaseVO.getBestMatchAppCaseQuestion());
        caseMatchVO.setAppCaseFirstQuestion(appCaseVO.getAppCaseFirstQuestion());
        caseMatchVO.setAppCase(appCaseVO.getAppCase());

        //把流程树里面带的条件值带到场景中（如：我要去北京出差，然后把北京带到关联的场景中，现在只能字符串拼接了）
        String flowLastAsk = matchRedisService.getFlowLastAsk(identification);
        if (flowLastAsk != null) {
            caseMatchVO = caseMatchVOHandlerService.initCaseMatchVO(caseMatchVO, request + flowLastAsk,identification);
        } else {
            caseMatchVO = caseMatchVOHandlerService.initCaseMatchVO(caseMatchVO, request,identification);
        }

        //匹配到场景，添加场景统计
        Integer matchedLogId = matchedLogHandlerService.saveCaseMatchedLog(companyId, identification, request, caseMatchVO.getAppCase().getId(), caseMatchVO.getAppCase().getContent());
        caseMatchVO.setMatchedId(matchedLogId);
        matchRedisService.saveCaseMatchVO(identification,caseMatchVO);

        //判断是第三方服务
        if (!caseMatchVO.getAppCase().getReplyType().equals(ReplyTypeConstant.NORMAL_REPLY)) {
            if(isInternalDeployed){
                return new ErrorMessage("内网部署,不开放接口");
            }
            return serviceMatchService.firstProcess(caseMatchVO, caseMatchVO.getAppCase().getCompanyId(), identification,isAnonymous);
        }

        return caseMatchService.firstProcess(caseMatchVO, companyId, identification, isAnonymous);
    }

    private Message noMatchHandler(Integer companyId,String identification,String request,boolean isAnonymous){
        String flowLastAsk = matchRedisService.getFlowLastAsk(identification);
        //如果匹配不到场景，尝试匹配快问快答，然后再匹配上个场景
        //匹配快问快答
        Message message=questionAnswerMatchService.response(companyId,request,identification,isAnonymous);
        if(message!=null){
            return message;
        }

        if (flowMatchService.getParentFlow(identification) == null) {
            matchRedisService.deleteFlowMatchVO(identification);
        }
        //继续匹配 匹配报销流程树
        //判断如果是流程树的末端，即没有pid等于它的数据
        message = flowMatchService.process(companyId, request, identification, isAnonymous);
        if (message != null) {
            return message;
        }

        //　匹配上次场景
        CaseMatchVO caseMatchVO = matchRedisService.getLastCase(identification);
        if(caseMatchVO==null){
            return null;
        }

        caseMatchVO.setAsk(request);
        caseMatchVO=caseMatchVOHandlerService.initCaseMatchVO(caseMatchVO,request,identification);

        matchRedisService.saveCaseMatchVO(identification,caseMatchVO);
        if (caseMatchVO != null) {
            // 对request的值做判断，如果在场景动作里面找到匹配值，则更新用户画像，并给场景回复
            if (flowLastAsk == null) {
                //判断是第三方服务
                if (!caseMatchVO.getAppCase().getReplyType().equals(ReplyTypeConstant.NORMAL_REPLY)) {
                    caseMatchVO.setAsk(request);
                    String firstQuestion=caseMatchVO.getAppCaseFirstQuestion();
                    if (serviceMatchService.lastCaseMatch(caseMatchVO, caseMatchVO.getAppCase().getCompanyId(), identification,isAnonymous)) {
                        caseMatchVO=matchRedisService.getCaseMatchVO(identification);
                        if(caseMatchVO!=null&&caseMatchVO.getNoBounded()){
                            matchRedisService.resetCaseMatchVO(identification);
                            matchRedisService.deleteLastCase(identification);
                            return new TextMessage(BOUNDED_REPLY);
                        }

                        if(caseMatchVO!=null){
                            return this.response(companyId,firstQuestion,identification,isAnonymous);
                        }
                    }

                } else {//通用场景

                    if (caseMatchService.lastCaseMatch(caseMatchVO, companyId, identification, request, isAnonymous)) {

                        if(caseMatchVO!=null&&caseMatchVO.getNoBounded()){
                            matchRedisService.resetCaseMatchVO(identification);
                            matchRedisService.deleteLastCase(identification);
                            return new TextMessage(BOUNDED_REPLY);
                        }

                        caseMatchVO=matchRedisService.getCaseMatchVO(identification);
                        if(caseMatchVO!=null){
                            return this.response(companyId,caseMatchVO.getAsk(),identification,isAnonymous);
                        }
                    }
                }
            }
        }

        matchRedisService.resetCaseMatchVO(identification);

        return null;

    }

    public AppCaseVO matchFlowAppCaseVO(AppCaseVO appCaseVO,Integer companyId,String identification,String request,boolean isAnonymous){
        if(appCaseVO==null||appCaseVO.getAppCase()==null){
            FlowMatchVO flowMatchVO = matchRedisService.getFlowMatchVO(identification);
            if(flowMatchVO!=null){
                List<Flow> lowerFlow = flowService.getLowerFlow(flowMatchVO.getCurrentFlow().getId(), null);
                for(Flow flow:lowerFlow){
                    if(flow!=null&&flow.getName()!=null&&flow.getName().equals(request)){
                        List<AppCase> appCaseList = appCaseService.getListByFlowId(flow.getId());
                        List<FlowQuestionAnswer> flowQuestionAnswerList = flowQuestionAnswerService.getList(flow.getId(), "");

                        appCaseVO= caseMatchService.getAppCaseVOAndFlowQuestionAnswerFromFlow(appCaseList,flowQuestionAnswerList,request);
                        break;
                    }
                }
            }
        }

        return appCaseVO;
    }

    public Message matchFlowQuestionAnswer(Integer companyId,String identification,String request,boolean isAnonymous){
        FlowMatchVO flowMatchVO = matchRedisService.getFlowMatchVO(identification);
        String  matchFlowQuestionAnswer=null;
        if(flowMatchVO!=null){
            List<Flow> lowerFlow = flowService.getLowerFlow(flowMatchVO.getCurrentFlow().getId(), null);
            for(Flow flow:lowerFlow){
                if(flow!=null&&flow.getName()!=null&&flow.getName().equals(request)){
                    List<AppCase> appCaseList = appCaseService.getListByFlowId(flow.getId());
                    List<FlowQuestionAnswer> flowQuestionAnswerList = flowQuestionAnswerService.getList(flow.getId(), "");

                    if(appCaseList!=null&&appCaseList.size()==0&&flowQuestionAnswerList!=null&&flowQuestionAnswerList.size()==1){
                        matchFlowQuestionAnswer=flowQuestionAnswerList.get(0).getQuestion().split("\n")[0];
                    }
                    break;
                }
            }
        }
        if(matchFlowQuestionAnswer!=null){
            return questionAnswerMatchService.response(companyId,matchFlowQuestionAnswer,identification,isAnonymous);
        }
        return null;
    }*/

}
