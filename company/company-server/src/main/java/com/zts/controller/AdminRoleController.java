package com.zts.controller;

import com.zts.entity.AdminRole;
import com.zts.entity.Dept;
import com.zts.service.AdminRoleService;
import com.zts.service.CompanyService;
import com.zts.service.DeptService;
import com.zts.vo.ReturnVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/adminRole")
public class AdminRoleController {

    @Resource
    private AdminRoleService adminRoleService;

    @Resource
    private DeptService deptService;

    @Resource
    private CompanyService companyService;

    @RequestMapping("/{id}")
    @ResponseBody
    public ReturnVO get(@PathVariable Integer id){
        AdminRole adminRole=adminRoleService.find(id);
        return ReturnVO.OK(adminRole);
    }

    @RequestMapping("/save")
    @ResponseBody
    public ReturnVO save(){
        AdminRole adminRole=new AdminRole();
        adminRole.setName("普通管理员");
        adminRole.setDescription("normal admin");

        adminRole.setCompany(companyService.find(1));

        Dept dept=deptService.find("2c9d808168eb25000168eb25eeba0002");
        Dept dept1=deptService.find("2c9d808168eb25000168eb25fd350003");

        List<Dept> deptList=new ArrayList<>();
        deptList.add(dept);
        deptList.add(dept1);

        adminRole.setDeptList(deptList);

        adminRoleService.save(adminRole);

        return ReturnVO.OK(adminRole);
    }

}
