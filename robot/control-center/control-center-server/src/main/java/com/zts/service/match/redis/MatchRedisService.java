package com.zts.service.match.redis;

import com.zts.util.redis.RedisUtils;
import com.zts.vo.CaseMatchVO;
import com.zts.vo.FlowMatchVO;
import com.zts.vo.ThirdPartyServiceVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.zts.service.match.constant.MatchConstant.*;

/**
 * @author zhangtusheng
 */
@Service
public class MatchRedisService {

    private static long timeout=1L;

    @Resource
    private RedisUtils<String, CaseMatchVO> redisUtilsCase;

    @Resource
    private RedisUtils<String,ThirdPartyServiceVO> redisUtilsService;

    @Resource
    private RedisUtils<String,FlowMatchVO> redisUtilsFlow;
    @Resource
    private RedisUtils<String,String>  redisUtilsFlowLastAsk;


    //清空对象
    public void resetCaseMatchVO(String identification) {
        //保存该次场景到上次场景
        CaseMatchVO caseMatchVO = this.getCaseMatchVO(identification);
        if (caseMatchVO != null) {
            saveLastCase(identification,caseMatchVO);
            deleteCaseMatchVO(identification);
        }
        if(getFlowLastAsk(identification)!=null){
            this.deleteFlowLastAsk(identification);
        }
    }

    public CaseMatchVO getLastCase(String identification){
        return redisUtilsCase.get(LAST_CASE + identification);
    }

    private void saveLastCase(String identification,CaseMatchVO caseMatchVO){
        redisUtilsCase.save(LAST_CASE + identification, caseMatchVO, timeout, TimeUnit.HOURS);
    }

    public void deleteLastCase(String identification){
        redisUtilsCase.delete(LAST_CASE + identification);
    }

    public FlowMatchVO getFlowMatchVO(String identification) {
        return redisUtilsFlow.get(FLOW_MATCH + identification);
    }

    public void saveFlowMatchVO(String identification, FlowMatchVO flowMatchVO) {
        redisUtilsFlow.save(FLOW_MATCH + identification, flowMatchVO, timeout, TimeUnit.HOURS);
    }

    @SuppressWarnings("unused")
    void deleteFlowMatchVO(String identification){
        redisUtilsFlow.delete(FLOW_MATCH + identification);
    }


    void saveFlowLastAsk(String identification,String flowLastAsk){
        redisUtilsFlowLastAsk.save(FLOW_LAST_ASK + identification, flowLastAsk, timeout, TimeUnit.HOURS);
    }

    String getFlowLastAsk(String identification){
        return redisUtilsFlowLastAsk.get(FLOW_LAST_ASK + identification);
    }

    private void deleteFlowLastAsk(String identification){
        redisUtilsFlowLastAsk.delete(FLOW_LAST_ASK + identification);
    }


    public void saveCaseMatchVO(String identification, CaseMatchVO caseMatchVO){
        redisUtilsCase.save(CASE_MATCH + identification, caseMatchVO, timeout, TimeUnit.HOURS);
    }

    private void deleteCaseMatchVO(String identification){
        redisUtilsCase.delete(CASE_MATCH + identification);
    }

    public CaseMatchVO getCaseMatchVO(String identification){
        return redisUtilsCase.get(CASE_MATCH + identification);
    }

    public void saveThirdPartyServiceVO(String identification, ThirdPartyServiceVO thirdPartyServiceVO){
        redisUtilsService.save(THIRD_PARTY_SERVICE_VO +identification,thirdPartyServiceVO,timeout,TimeUnit.HOURS);
    }

    public ThirdPartyServiceVO getThirdPartyServiceVO(String identification){
        return redisUtilsService.get(THIRD_PARTY_SERVICE_VO + identification);
    }

    public void deleteThirdPartyServiceVO(String identification){
        redisUtilsService.delete(THIRD_PARTY_SERVICE_VO +identification);
    }
}
