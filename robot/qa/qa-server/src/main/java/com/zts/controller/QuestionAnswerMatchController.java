package com.zts.controller;

import com.zts.service.match.QuestionAnswerMatchService;
import com.zts.vo.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping(value = "/questionAnswerMatch")
public class QuestionAnswerMatchController {

    @Resource
    private QuestionAnswerMatchService questionAnswerMatchService;

    @RequestMapping("/match")
    @ResponseBody
    public Message match(String request){
        return questionAnswerMatchService.response(1,request,"1");
    }
}
