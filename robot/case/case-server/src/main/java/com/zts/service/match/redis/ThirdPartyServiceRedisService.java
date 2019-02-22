package com.zts.service.match.redis;

import com.zts.vo.ThirdPartyServiceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author zhangtusheng
 */
@Slf4j
@Service
public class ThirdPartyServiceRedisService {

    @Resource
    private CaseMatchRedisService caseMatchRedisService;

    public ThirdPartyServiceVO putParam(String identification, String paramKey, String paramValue){

        ThirdPartyServiceVO thirdPartyServiceVO = caseMatchRedisService.getThirdPartyServiceVO(identification);

        if(thirdPartyServiceVO==null){
            log.error("第三方服务没有初始化ThirdPartyVO");
            return null;
        }

        thirdPartyServiceVO.getParam().put(paramKey,paramValue);

        caseMatchRedisService.saveThirdPartyServiceVO(identification,thirdPartyServiceVO);

        return thirdPartyServiceVO;
    }

    public void initThirdPartyServiceVO(Integer companyId,String identification,Integer serviceId){
        ThirdPartyServiceVO thirdPartyServiceVO =new ThirdPartyServiceVO();
        thirdPartyServiceVO.setCompanyId(companyId);
        thirdPartyServiceVO.setServiceId(serviceId);
        thirdPartyServiceVO.setUserId(identification);
        thirdPartyServiceVO.setParam(new HashMap<>(2));

        caseMatchRedisService.saveThirdPartyServiceVO(identification,thirdPartyServiceVO);

    }

    public void initThirdPartyCompanyServiceVO(Integer companyId,String identification,Integer compnayServiceId){
        ThirdPartyServiceVO thirdPartyServiceVO =new ThirdPartyServiceVO();
        thirdPartyServiceVO.setCompanyId(companyId);
        thirdPartyServiceVO.setCompanyServiceId(compnayServiceId);
        thirdPartyServiceVO.setUserId(identification);
        thirdPartyServiceVO.setParam(new HashMap<>(2));

        caseMatchRedisService.saveThirdPartyServiceVO(identification,thirdPartyServiceVO);

    }
}
