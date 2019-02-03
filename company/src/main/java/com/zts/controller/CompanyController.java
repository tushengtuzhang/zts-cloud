package com.zts.controller;

import com.zts.entity.Company;
import com.zts.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public List<Company> list(){
        return companyService.findAll();
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false,defaultValue = "zts") String name) {
        return "hello "+name+"，this is new world";
    }
}
