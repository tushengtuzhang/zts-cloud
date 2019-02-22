package com.zts.service;

import com.zts.dao.MatchedLogDao;
import com.zts.entity.MatchedLog;
import com.zts.feign.CompanyFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class MatchedLogService {

    @Resource
    private MatchedLogDao matchedLogDao;

    @Resource
    private CompanyFeign companyFeign;

    public Integer saveMatchedLog(Integer companyId, String identification, String input, String matchedType,String matchedId,String matchedName){

        String companyName=companyFeign.get(companyId).getName();
        String userName=companyFeign.getUserNameByIdentification(identification);

        MatchedLog matchedLog = new MatchedLog(companyId, companyName, identification, userName, input, matchedType,
                matchedId, matchedName);
        matchedLogDao.save(matchedLog);

        return matchedLog.getId();

    }


}
