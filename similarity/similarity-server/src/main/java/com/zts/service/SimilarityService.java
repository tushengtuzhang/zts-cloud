package com.zts.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zts.constant.RemoteConstant;
import com.zts.entity.*;
import com.zts.feign.CaseFeign;
import com.zts.feign.CompanyFeign;
import com.zts.feign.FlowFeign;
import com.zts.feign.QuestionAnswerFeign;
import com.zts.util.slot.SlotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zts.constant.RemoteConstant.*;

/**
 * @author zhangtusheng
 */
@Slf4j
@Service
public class SimilarityService {

    @Value("${similarity.remote.url}")
    private String remoteUrl;

    @Value("${similarity.remote.isProduction}")
    private boolean isProduction;

    @Resource
    private CompanyFeign companyFeign;
    @Resource
    private CaseFeign caseFeign;
    @Resource
    private FlowFeign flowFeign;
    @Resource
    private QuestionAnswerFeign questionAnswerFeign;

    private Gson gson = new Gson();

    @Resource
    private RestTemplate restTemplate;

    public void clearAndInitData(Integer companyId){
        clearCompany(companyId);
        initData(companyId);
    }

    public void clearCompany(Integer companyId){

        log.info("--clearCompany--"+companyId);

        Company company=companyFeign.get(companyId);
        String companyCode=company.getCode();

        List<String> clearCompany=new ArrayList<>();
        clearCompany.add(companyCode);

        String string = gson.toJson(clearCompany);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(remoteUrl + "/clear_company", string, String.class);
        log.info(responseEntity.getBody());

    }

    public void initData() {
        List<Company> companyList = companyFeign.getAll();
        companyList.forEach(company -> {
            initData(company.getId());
            //initHotWord(company.getId());
        });
    }

    public void initData(Integer companyId){
        log.info("--initData--"+companyId);

        if(isProduction){
            clearCompany(companyId);
        }

        List<AppCase> appCaseList = caseFeign.getListByCompanyId(companyId);
        List<Flow> flowList = flowFeign.getListByCompanyId(companyId);
        List<QuestionAnswer> questionAnswerList = questionAnswerFeign.getListByCompanyId(companyId);

        if (appCaseList != null && appCaseList.size() > 0) {
            initDataCase(companyId, appCaseList);
        }
        if (flowList != null && flowList.size() > 0) {
            initDataFlow(companyId, flowList);
        }
        if (questionAnswerList != null && questionAnswerList.size() > 0) {
            initDataQA(companyId, questionAnswerList);
        }
    }

    private void initDataCase(Integer companyId, List<AppCase> appCaseList) {

        RemoteVO remoteVO = new RemoteVO();
        remoteVO.setCompany_code(companyFeign.get(companyId).getCode());
        remoteVO.setType(CASE_TYPE);

        List<TextData> textDataList = new ArrayList<>();

        appCaseList.forEach(appCase -> {

            Integer id = appCase.getId();

            TextData textData = new TextData(CASE_PREFIX + id + "_0", preHandler(appCase.getContent()));
            textDataList.add(textData);

            appCase.getCaseQuestionList().forEach(appCaseQuestion -> {

                if(appCaseQuestion!=null&&appCaseQuestion.getQuestion()!=null){
                    if(!SlotUtils.isSlotQuestion(appCaseQuestion.getQuestion())){
                        TextData textData1 = new TextData(CASE_PREFIX + id + "_" + appCaseQuestion.getId(), preHandler(appCaseQuestion.getQuestion()));
                        textDataList.add(textData1);
                    }
                }

            });
        });

        remoteVO.setText_data(textDataList);

        String string = gson.toJson(remoteVO);

        this.jsonPost(remoteUrl + "/add", string);

    }

    /**
     * 组拼流程树及流程问题初始化数据
     *
     * @param companyId 公司ID
     * @param flowList  流程列表
     */
    private void initDataFlow(Integer companyId, List<Flow> flowList) {

        RemoteVO remoteVO = new RemoteVO();
        remoteVO.setCompany_code(companyFeign.get(companyId).getCode());
        remoteVO.setType(FLOW_TYPE);

        List<TextData> textDataList = new ArrayList<>();

        flowList.forEach(flow -> {

            Integer id = flow.getId();

            TextData textData = new TextData(FLOW_PREFIX + id + "_0", preHandler(flow.getName()));
            textDataList.add(textData);

            flow.getFlowQuestionList().forEach(flowQuestion -> {
                TextData textData1 = new TextData(FLOW_PREFIX + id + "_" + flowQuestion.getId(), preHandler(flowQuestion.getQuestion()));
                textDataList.add(textData1);
            });
        });

        remoteVO.setText_data(textDataList);

        String string = gson.toJson(remoteVO);
        this.jsonPost(remoteUrl + "/add", string);
    }

    /**
     * 组拼快问快答初始化数据
     *
     * @param companyId          公司ID
     * @param questionAnswerList 快问快答列表
     */
    private void initDataQA(Integer companyId, List<QuestionAnswer> questionAnswerList) {

        RemoteVO remoteVO = new RemoteVO();
        remoteVO.setCompany_code(companyFeign.get(companyId).getCode());
        remoteVO.setType(QA_TYPE);

        List<TextData> textDataList = new ArrayList<>();

        questionAnswerList.forEach(questionAnswer -> {
            String[] questionStringArray = questionAnswer.getQuestion().split("\n");
            for (int j = 0; j < questionStringArray.length; j++) {
                TextData textData1 = new TextData(QA_PREFIX + questionAnswer.getId() + "_" + j, preHandler(questionStringArray[j]));
                textDataList.add(textData1);
            }
        });

        remoteVO.setText_data(textDataList);

        String string = gson.toJson(remoteVO);
        this.jsonPost(remoteUrl + "/add", string);
    }

    private ResponseEntity<String> jsonPost(String url, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            return responseEntity;
        }else{
            return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }
    }


    public QuestionAnswer getBestMatchQA(Integer companyId, String question) {
        List<Score> scoreList = this.similarity(companyId, QA_TYPE, 1, question, null);
        if (scoreList != null && scoreList.size() > 0) {
            String[] idList = scoreList.get(0).getId().split("_");
            QuestionAnswer questionAnswer = questionAnswerFeign.get(Integer.valueOf(idList[1]));
            if (questionAnswer != null) {
                questionAnswer.setScore(scoreList.get(0).getScore());

                String[] questionArray = questionAnswer.getQuestion().split("\n");
                for (int i = 0; i < questionArray.length; i++) {
                    if (Integer.parseInt(idList[2]) == i) {
                        questionAnswer.setBestMatchQuestion(questionArray[i]);
                        break;
                    }
                }
                if (questionAnswer.getBestMatchQuestion() == null) {
                    questionAnswer.setBestMatchQuestion(questionArray[0]);
                }


                return questionAnswer;
            }
        }
        return new QuestionAnswer();
    }

    public List<ScoreVO> similarityQA(Integer companyId, Integer count, String question, Double minScore) {
        List<Score> scoreList = this.similarity(companyId, QA_TYPE, count, question, null);

        List<ScoreVO> scoreVOList = new ArrayList<>();
        scoreList.forEach(score -> {
            String[] idList = score.getId().split("_");
            QuestionAnswer questionAnswer = questionAnswerFeign.get(Integer.valueOf(idList[1]));
            if(questionAnswer==null){
                this.deleteData(companyId, RemoteConstant.QA_TYPE,score.getId());
            }else{
                String[] questionArray = questionAnswer.getQuestion().split("\n");

                ScoreVO scoreVO = new ScoreVO();

                if (minScore == null) {
                    scoreVO = getScoreVO(score, idList, questionArray, scoreVO);

                    scoreVOList.add(scoreVO);
                } else {
                    if (score.getScore() > minScore) {
                        scoreVO = getScoreVO(score, idList, questionArray, scoreVO);

                        scoreVOList.add(scoreVO);
                    }
                }
            }
        });

        return scoreVOList;
    }

    private ScoreVO getScoreVO(Score score, String[] idList, String[] questionArray, ScoreVO scoreVO) {
        if (Integer.parseInt(idList[2]) == 0) {
            scoreVO = new ScoreVO(questionArray[0], score.getScore());
        } else {
            for (int i = 0; i < questionArray.length; i++) {
                if (i == Integer.parseInt(idList[2])) {
                    scoreVO = new ScoreVO(questionArray[i], score.getScore());
                    break;
                }
            }
        }
        return scoreVO;
    }


    public List<ScoreVO> similarityCase(Integer companyId, Integer count, String question, Double minScore) {
        List<Score> scoreList = this.similarity(companyId, CASE_TYPE, count, question, null);

        List<ScoreVO> scoreVOList = new ArrayList<>();
        scoreList.forEach(score -> {
            String[] idList = score.getId().split("_");
            List<AppCaseQuestion> appCaseQuestionList = caseFeign.getCaseQuestionListByCaseId(Integer.valueOf(idList[1]));
            ScoreVO scoreVO;
            if(appCaseQuestionList!=null&&appCaseQuestionList.size()>0){
                if (minScore == null) {
                    if (Integer.parseInt(idList[2]) == 0) {
                        scoreVO = new ScoreVO(appCaseQuestionList.get(0).getQuestion(), score.getScore());
                    } else {
                        scoreVO = new ScoreVO(caseFeign.getCaseQuestionById(Integer.valueOf(idList[2])).getQuestion(), score.getScore());
                    }
                    scoreVOList.add(scoreVO);
                } else {
                    if (score.getScore() > minScore) {
                        if (Integer.parseInt(idList[2]) == 0) {
                            scoreVO = new ScoreVO(appCaseQuestionList.get(0).getQuestion(), score.getScore());
                        } else {
                            scoreVO = new ScoreVO(caseFeign.getCaseQuestionById(Integer.valueOf(idList[2])).getQuestion(), score.getScore());
                        }
                        scoreVOList.add(scoreVO);
                    }
                }
            }else{
                this.deleteData(companyId,CASE_TYPE,score.getId());
            }

        });

        return scoreVOList;
    }


    public List<ScoreVO> similarityFlow(Integer companyId, Integer count, String question, Double minScore) {
        List<Score> scoreList = this.similarity(companyId, FLOW_TYPE, count, question, null);

        List<ScoreVO> scoreVOList = new ArrayList<>();
        scoreList.forEach(score -> {
            if (minScore != null) {
                if (score.getScore() > minScore) {
                    String[] idList = score.getId().split("_");
                    Flow flow = flowFeign.get(Integer.valueOf(idList[1]));
                    if(flow!=null){
                        ScoreVO scoreVO = new ScoreVO(flow.getName(), score.getScore());
                        scoreVOList.add(scoreVO);
                    }else{
                        this.deleteData(companyId,FLOW_TYPE,score.getId());
                    }

                }
            } else {
                String[] idList = scoreList.get(0).getId().split("_");
                Flow flow = flowFeign.get(Integer.valueOf(idList[1]));
                if(flow!=null){
                    ScoreVO scoreVO = new ScoreVO(flow.getName(), score.getScore());
                    scoreVOList.add(scoreVO);
                }else{
                    this.deleteData(companyId,FLOW_TYPE,score.getId());
                }

            }
        });

        return scoreVOList;
    }

    /**
     * @param companyId  公司Id
     * @param question   问题
     * @param queryCount 查询数量
     * @param type       查找的类型 c,f,q
     * @param ids        如果ids不为空，则在ids里面查找，type失效
     * @return return List<Score>
     */
    public List<Score> similarityIncludeIndustry(Integer companyId, String type, Integer queryCount, String question, List<String> ids) {
        List<Score> allScoreList=new ArrayList<>();
        List<CompanyIndustryRelation> companyIndustryRelationList = companyFeign.get(companyId).getCompanyIndustryRelationList();
        for (CompanyIndustryRelation companyIndustryRelation : companyIndustryRelationList) {

            if(companyIndustryRelation.getRefCompany().getStatus()){
                Integer refCompanyId = companyIndustryRelation.getRefCompany().getId();
                Company company=companyFeign.get(refCompanyId);
                if(company!=null&&company.getStatus()&&company.getAvailableTag()==0){
                    List<Score> industryScoreList=similarityIncludeIndustry(refCompanyId,type,queryCount,question,null);
                    if(industryScoreList!=null&&industryScoreList.size()>1){
                        allScoreList.addAll(industryScoreList);
                    }
                }
                List<Score> industryScoreList=similarityIncludeIndustry(refCompanyId,type,queryCount,question,ids);
                if(industryScoreList!=null&&industryScoreList.size()>1){
                    allScoreList.addAll(industryScoreList);
                }
            }
        }

        SimilarityVO similarityVO = new SimilarityVO(companyFeign.get(companyId).getCode(), type, queryCount, preHandler(question), ids);

        String string = gson.toJson(similarityVO);

        log.info("similarity send:"+string);

        ResponseEntity<String> response = this.jsonPost(remoteUrl + "/infer", string);

        log.info("similarity response:"+response.getBody());

        List<Score> scoreList = gson.fromJson(response.getBody(), new TypeToken<List<Score>>() {
        }.getType());

        allScoreList.addAll(scoreList);


        if (allScoreList.size() > 1) {
            allScoreList = getBestScoreList(allScoreList);
            quickSortByList(allScoreList,0,allScoreList.size()-1);
        }

        if(allScoreList.size()>queryCount){
            return allScoreList.subList(0,queryCount);
        }else{
            return allScoreList;
        }
    }


    public List<Score> similarity(Integer companyId, String type, Integer queryCount, String question, List<String> ids) {
        List<Score> allScoreList=similarityIncludeIndustry(companyId,type,queryCount,question,ids);
        if (allScoreList.size() > 1) {
            allScoreList = getBestScoreList(allScoreList);
            quickSortByList(allScoreList,0,allScoreList.size()-1);
        }

        if(allScoreList.size()>queryCount){
            return allScoreList.subList(0,queryCount);
        }else{
            return allScoreList;
        }
    }


    private List<Score> getBestScoreList(List<Score> scoreList) {
        List<String> firstIdList = new ArrayList<>();

        for (Score sc : scoreList) {
            String[] idList = sc.getId().split("_");
            String firstId = idList[1];
            firstIdList.add(firstId);
        }

        //去重
        HashSet<String> firstIdHashSet = new LinkedHashSet<>(firstIdList);
        firstIdList.clear();
        firstIdList.addAll(firstIdHashSet);

        List<Score> newScoreList = new ArrayList<>();
        for (String fId : firstIdList) {
            for (Score sc : scoreList) {
                String[] idList = sc.getId().split("_");
                String firstId = idList[1];
                if (fId.equals(firstId)) {
                    newScoreList.add(sc);
                    break;
                }
            }
        }

        return newScoreList;
    }

    private void quickSortByList(List<Score> list, int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        if (lo >= hi){
            return;
        }
        //确定指针方向的逻辑变量
        boolean transfer=true;

        while (lo != hi) {
            if (list.get(lo).getScore() < list.get(hi).getScore()) {
                //交换
                Score temp = list.get(lo);
                list.set(lo, list.get(hi));
                list.set(hi, temp);
                //决定下标移动，还是上标移动
                transfer=!transfer;
            }

            //将指针向前或者向后移动
            if(transfer) {
                hi--;
            }else{
                lo++;
            }
        }

        //将数组分开两半，确定每个数字的正确位置
        lo--;
        hi++;
        quickSortByList(list, lo0, lo);
        quickSortByList(list, hi, hi0);
    }

    public void deleteData(Integer companyId,String type,String id){
        RemoteDeleteVO remoteDeleteVO = new RemoteDeleteVO();
        remoteDeleteVO.setCompany_code(companyFeign.get(companyId).getCode());
        remoteDeleteVO.setType(type);

        List<String> deleteIdList = new ArrayList<>();
        deleteIdList.add(id);
        remoteDeleteVO.setDelete_id(deleteIdList);

        Gson gson=new Gson();
        String string = gson.toJson(remoteDeleteVO);
        this.jsonPost(remoteUrl + "/delete", string);
    }


    private String preHandler(String request){
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(request);
        request=m.replaceAll("").trim();

        request=request.replaceAll(" ","");
        request=request.replaceAll("-","");

        return request;
    }
}
