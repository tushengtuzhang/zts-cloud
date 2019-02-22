package com.zts.controller;

import com.zts.service.MatchedLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@RequestMapping(value = "/matchedLog")
@RestController
public class MatchedLogController {

    @Resource
    private MatchedLogService matchedLogService;

    @RequestMapping("/saveMatchedLog")
    @ResponseBody
    public Integer saveCaseMatchedLog(Integer companyId,String identification,String input,String matchedType,String matchedId,String matchedName){

        return matchedLogService.saveMatchedLog(companyId,identification,input,matchedType,matchedId,matchedName);
    }


}
