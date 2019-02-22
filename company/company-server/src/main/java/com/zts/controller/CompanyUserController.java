package com.zts.controller;

import com.zts.entity.CompanyUser;
import com.zts.service.CompanyUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/companyUser")
public class CompanyUserController {

    @Resource
    private CompanyUserService companyUSerService;

    @RequestMapping("/getUserNameByIdentification")
    @ResponseBody
    public String getUserNameByIdentification(){
        String identification="2c91808365f0d7b40165f0d7b4b50000";
        return companyUSerService.getUserNameByIdentification(identification);
    }

}
