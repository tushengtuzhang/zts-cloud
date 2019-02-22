package com.zts.feign;

import com.zts.entity.QuestionAnswer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangtusheng
 */
@FeignClient(value="qa-server")
public interface QuestionAnswerFeign {

    /**
     * 根据公司Id获取列表
     * @param companyId 公司Id
     * @return list
     */
    @RequestMapping("/questionAnswer/getListByCompanyId")
    List<QuestionAnswer> getListByCompanyId(@RequestParam(value = "companyId") Integer companyId);

    @RequestMapping("/questionAnswer/get/{id}")
    QuestionAnswer get(@PathVariable(value = "id") Integer id);
}
