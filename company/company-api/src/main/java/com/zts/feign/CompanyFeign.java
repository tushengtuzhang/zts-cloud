package com.zts.feign;

import com.zts.entity.Company;
import com.zts.entity.CompanyUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zhangtusheng
 */
@FeignClient(value = "company-server"/*,fallback = CompanyHystrix.class*/)
public interface CompanyFeign {

    /**
     * 获取所有公司列表
     * @return list
     */
    @GetMapping("list")
    List<Company> getAll();

    /**
     * 根据公司Id获取公司信息
     * @param companyId
     * @return company
     */
    @GetMapping("/company/get/{companyId}")
    Company get(@PathVariable(value = "companyId") Integer companyId);

    @RequestMapping("/companyUser/getUserNameByIdentification")
    String getUserNameByIdentification(String identification);

}
