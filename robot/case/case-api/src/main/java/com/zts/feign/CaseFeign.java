package com.zts.feign;

import com.zts.entity.AppCase;
import com.zts.entity.AppCaseQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangtusheng
 */
@FeignClient(value = "case-server")
public interface CaseFeign {
    @RequestMapping("/appCase/get/{id}")
    AppCase get(@PathVariable(value="id") Integer id);
    /**
     * 根据公司Id获取列表
     * @param companyId 公司Id
     * @return list
     */
    @RequestMapping("/appCase/getListByCompanyId")
    List<AppCase> getListByCompanyId(@RequestParam(value = "companyId") Integer companyId);

    @RequestMapping("/appCaseQuestion/get")
    AppCaseQuestion getCaseQuestionById(@RequestParam(value="id") Integer id);

    @RequestMapping("/appCaseQuestion/getListByCaseId")
    List<AppCaseQuestion> getCaseQuestionListByCaseId(@RequestParam(value="caseId") Integer caseId);
}
