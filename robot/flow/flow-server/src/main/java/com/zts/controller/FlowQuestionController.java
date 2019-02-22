package com.zts.controller;

import com.zts.entity.Flow;
import com.zts.entity.FlowQuestion;
import com.zts.service.FlowQuestionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/flowQuestion")
public class FlowQuestionController {

    @Resource
    private FlowQuestionService flowQuestionService;

    @RequestMapping(value = "/get/{id}")
    @ResponseBody
    public FlowQuestion get(@PathVariable Integer id){
        return flowQuestionService.find(id);
    }
}
