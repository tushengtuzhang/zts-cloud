package com.zts;

import com.zts.entity.AdminUser;
import com.zts.entity.Company;
import com.zts.entity.Dept;
import com.zts.service.AdminUserService;
import com.zts.service.CompanyService;
import com.zts.service.DeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCompanyApplication {

    @Resource
    private CompanyService companyService;
    @Resource
    private DeptService deptService;

    @Resource
    private AdminUserService adminUserService;

    @Test
    public void saveDept(){
        Company company=companyService.find(1);
        AdminUser adminUser=adminUserService.find(1);

        Dept dept=new Dept();
        dept.setCompany(company);
        dept.setName("部门");
        dept.setCreateUser(adminUser);
        deptService.save(dept);
    }
}
