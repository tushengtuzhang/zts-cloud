package com.zts.feign;

import com.zts.entity.Company;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangtusheng
 */
@Component
public class CompanyHystrix implements CompanyFeign{


    @Override
    public List<Company> getAll() {
        return null;
    }

    @Override
    public Company get(Long companyId) {
        return null;
    }
}
