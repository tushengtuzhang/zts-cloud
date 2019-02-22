package com.zts.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangtusheng
 */
@FeignClient(value = "robot-common-server")
public interface RobotCommonFeign {

    @RequestMapping("/companyConfig/getByVariable")
    String getByVariable(@RequestParam("companyId") Integer companyId,@RequestParam("variable") String variable);


    @RequestMapping("/matchedLog/saveMatchedLog")
    public Integer saveMatchedLog(@RequestParam("companyId") Integer companyId,@RequestParam("identification") String identification,
                                  @RequestParam("input") String input,@RequestParam("matchedType") String matchedType,
                                  @RequestParam("matchedId") String matchedId,@RequestParam("matchedName") String matchName);

}
