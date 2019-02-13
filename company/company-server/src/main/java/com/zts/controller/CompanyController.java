package com.zts.controller;

import com.zts.entity.Company;
import com.zts.entity.CompanyUser;
import com.zts.entity.Status;
import com.zts.service.CompanyService;
import com.zts.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @Value("${name}")
    private String projectName;

    @Resource
    private RedisUtil<String, CompanyUser> companyUserRedisUtil;

    @GetMapping("/list")
    public List<Company> list(){
        return companyService.findAll();
    }

    @GetMapping("{companyId}")
    public Company get(@PathVariable Integer companyId){
        System.out.println("load1");

        return companyService.find(companyId);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false,defaultValue = "zts") String name) {
        return "hello "+name+"，this is new world from "+projectName;
    }
}
