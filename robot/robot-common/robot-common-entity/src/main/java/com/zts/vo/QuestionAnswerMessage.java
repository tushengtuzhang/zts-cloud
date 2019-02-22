package com.zts.vo;

import java.util.List;

public class QuestionAnswerMessage implements Message {

    private Integer id;

    private String question;
    /**
     * 富文本内容
     */
    private String answer;

    private List<String> bubbleList;

    private Integer matchedLogId;

    public QuestionAnswerMessage(Integer id, String question, String answer, List<String> bubbleList,Integer matchedLogId) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.bubbleList = bubbleList;
        this.matchedLogId=matchedLogId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getBubbleList() {
        return bubbleList;
    }

    public void setBubbleList(List<String> bubbleList) {
        this.bubbleList = bubbleList;
    }

    public Integer getMatchedLogId() {
        return matchedLogId;
    }

    public void setMatchedLogId(Integer matchedLogId) {
        this.matchedLogId = matchedLogId;
    }
}
