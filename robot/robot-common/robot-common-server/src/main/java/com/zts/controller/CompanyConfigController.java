package com.zts.controller;

import com.zts.service.CompanyConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@RequestMapping(value = "/companyConfig")
@RestController
public class CompanyConfigController {

    @Resource
    private CompanyConfigService companyConfigService;

    @RequestMapping("/getByVariable")
    @ResponseBody
    public String getByVariable(Integer companyId,String variable){
        return companyConfigService.getByVariable(companyId,variable);
    }


}
