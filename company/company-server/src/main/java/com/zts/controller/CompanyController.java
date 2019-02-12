package com.zts.controller;

import com.zts.entity.Company;
import com.zts.service.CompanyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @GetMapping("/list")
    public List<Company> list(){
        return companyService.findAll();
    }

    @GetMapping("{companyId}")
    public Company get(@PathVariable Long companyId){

        return companyService.find(companyId);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false,defaultValue = "zts") String name) {
        return "hello "+name+"，this is new world from "+projectName;
    }
}
