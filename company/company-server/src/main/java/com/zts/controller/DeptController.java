package com.zts.controller;

import com.zts.entity.AdminUser;
import com.zts.entity.Company;
import com.zts.entity.Dept;
import com.zts.service.AdminUserService;
import com.zts.service.CompanyService;
import com.zts.service.DeptService;
import com.zts.vo.ReturnVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @Resource
    private CompanyService companyService;

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping("/{id}")
    @ResponseBody
    public ReturnVO get(@PathVariable String id){
        Dept dept=deptService.find(id);
        return ReturnVO.OK(dept);
    }

    @RequestMapping("/save")
    @ResponseBody
    public ReturnVO save(){
        Company company=companyService.find(1);
        AdminUser adminUser=adminUserService.find(1);

        Dept dept=new Dept();
        dept.setCompany(company);
        dept.setName("部门");
        dept.setCreateUser(adminUser);
        deptService.save(dept);


        return ReturnVO.OK(dept);
    }

    @RequestMapping(value="/listAll")
    @ResponseBody
    public List<Dept> listAll(){
        Integer userId=1;


        AdminUser adminUser=adminUserService.find(userId);

        if(adminUser.getCompany().getId()==0){
            return null;
        }

        return deptService.selectListByCompanyId(adminUser.getCompany().getId());

    }
}
