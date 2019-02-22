package com.zts.feign;

import com.hankcs.hanlp.seg.common.Term;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangtusheng
 */
@FeignClient(value = "case-server")
public interface NlpFeign {

    @RequestMapping(value = "/nlp/updateDictionary")
    void updateDictionary(@RequestParam("companyId") Integer companyId,@RequestParam("text") String text);

    @RequestMapping(value = "/nlp/segment")
    List<Term> segment(@RequestParam("companyId") Integer companyId,@RequestParam("text") String text);
}
