package com.zts.feign;

import com.zts.entity.AppCase;
import com.zts.entity.Flow;
import com.zts.entity.FlowQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangtusheng
 */
@FeignClient(value="flow-server")
public interface FlowFeign {

    /**
     * 根据公司Id获取列表
     * @param companyId 公司Id
     * @return list
     */
    @RequestMapping("/flow/getListByCompanyId")
    List<Flow> getListByCompanyId(@RequestParam(value = "companyId") Integer companyId);

    @RequestMapping("/flow/get/{id}")
    Flow get(@PathVariable(value = "id") Integer id);

    @RequestMapping("/flowQuestion/{id}")
    FlowQuestion getFlowQuestionById(@PathVariable(value = "id") Integer id);
}
