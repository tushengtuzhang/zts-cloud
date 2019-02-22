package com.zts.controller;

import com.zts.constant.RemoteConstant;
import com.zts.entity.*;
import com.zts.feign.CaseFeign;
import com.zts.feign.FlowFeign;
import com.zts.feign.QuestionAnswerFeign;
import com.zts.service.SimilarityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping(value="/similarity")
public class SimilarityController {

    @Resource
    private SimilarityService similarityService;

    @Resource
    private FlowFeign flowFeign;
    @Resource
    private CaseFeign caseFeign;
    @Resource
    private QuestionAnswerFeign questionAnswerFeign;

    @RequestMapping(value = "/initData")
    @ResponseBody
    public String initData(){
        Integer companyId=1;
        similarityService.clearAndInitData(companyId);
        return "init data done";
    }

    @RequestMapping(value = "/similarity")
    @ResponseBody
    public List<ResponseVO> similarity(String request, @RequestParam(required = false) String type, @RequestParam(required = false,defaultValue = "30") Integer count){

        Integer companyId=1;

        List<Score> scoreList = similarityService.similarity(companyId, type, count, request, null);

        List<ResponseVO> responseVOList=new ArrayList<>();

        if(scoreList!=null&&scoreList.size()>0){
            for(Score score:scoreList){
                String[] idList = score.getId().split("_");
                switch(idList[0]){
                    case RemoteConstant.CASE_TYPE:
                        if(idList[2].equals("0")){
                            AppCase appCase=caseFeign.get(Integer.parseInt(idList[1]));
                            if(appCase!=null){
                                responseVOList.add(new ResponseVO(score.getId(),appCase.getContent(),score.getScore()));
                            }
                        }else{
                            AppCaseQuestion appCaseQuestion=caseFeign.getCaseQuestionById(Integer.parseInt(idList[2]));
                            if(appCaseQuestion!=null){
                                responseVOList.add(new ResponseVO(score.getId(),appCaseQuestion.getQuestion(),score.getScore()));
                            }else{
                                responseVOList.add(new ResponseVO(score.getId(),"未同步数据（奇怪了）",score.getScore()));
                            }
                        }
                        break;
                    case RemoteConstant.FLOW_TYPE:
                        if(idList[2].equals("0")){
                            Flow flow=flowFeign.get(Integer.parseInt(idList[1]));
                            if(flow!=null){
                                responseVOList.add(new ResponseVO(score.getId(),flow.getName(),score.getScore()));
                            }
                        }else{
                            FlowQuestion flowQuestion=flowFeign.getFlowQuestionById(Integer.parseInt(idList[2]));
                            if(flowQuestion!=null){
                                responseVOList.add(new ResponseVO(score.getId(),flowQuestion.getQuestion(),score.getScore()));
                            }else{
                                responseVOList.add(new ResponseVO(score.getId(),"未同步数据（奇怪了）",score.getScore()));
                            }
                        }
                        break;
                    case RemoteConstant.QA_TYPE:
                        QuestionAnswer questionAnswer=questionAnswerFeign.get(Integer.parseInt(idList[1]));
                        if(questionAnswer!=null){
                            String[] split = questionAnswer.getQuestion().split("\n");
                            if(Integer.parseInt(idList[2])<split.length){
                                String s = split[Integer.parseInt(idList[2])];
                                responseVOList.add(new ResponseVO(score.getId(),s,score.getScore()));
                            }

                        }
                        break;
                    default:
                        break;

                }
            }
        }


        return responseVOList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class ResponseVO{
        private String id;
        private String name;
        private Double score;
    }

    @RequestMapping("/getBestMatchQA")
    @ResponseBody
    public QuestionAnswer getBestMatchQA(Integer companyId, String question){
        return similarityService.getBestMatchQA(companyId,question);
    }
}
