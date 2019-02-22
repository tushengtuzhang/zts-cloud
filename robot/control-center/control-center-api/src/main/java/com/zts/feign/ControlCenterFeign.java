package com.zts.feign;

import com.zts.entity.Flow;
import com.zts.entity.QuestionAnswer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangtusheng
 */
@FeignClient(value = "control-center-server")
public interface ControlCenterFeign {


}
