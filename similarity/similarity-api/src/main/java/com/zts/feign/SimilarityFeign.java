package com.zts.feign;

import com.zts.entity.QuestionAnswer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangtusheng
 */
@FeignClient("similarity-server")
public interface SimilarityFeign {

    @RequestMapping("/similarity/getBestMatchQA")
    QuestionAnswer getBestMatchQA(@RequestParam("companyId") Integer companyId,@RequestParam("question") String question);
}
