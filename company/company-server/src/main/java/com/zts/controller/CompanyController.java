package com.zts.controller;

import com.zts.entity.Company;
import com.zts.entity.CompanyUser;
import com.zts.service.CompanyService;
import com.zts.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private RedisUtils<String, CompanyUser> companyUserRedisUtils;

    @GetMapping("/list")
    public List<Company> list(){
        return companyService.findAll();
    }

    @GetMapping("/get/{companyId}")
    public Company get(@PathVariable Integer companyId,HttpServletRequest request){
        System.out.println("load1");

        //请求最终在哪个端口结束
        System.out.println(request.getLocalPort());
        //请求原始发送给哪个服务端口
        System.out.println(request.getServerPort());

        return companyService.find(companyId);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false,defaultValue = "zts") String name, HttpServletRequest request) {
        return "hello "+name+"，this is new world from "+projectName+request;
    }
}
