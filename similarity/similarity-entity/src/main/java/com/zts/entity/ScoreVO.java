package com.zts.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 返回到服务器，供mobile调用的scoreVO
 * @author zhangtusheng
 */
public class ScoreVO implements Serializable {

    private String name;
    private Double score;
    /**拆词*/
    private List<String> segmentList;

    public ScoreVO() {

    }

    public ScoreVO(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<String> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(List<String> segmentList) {
        this.segmentList = segmentList;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScoreVO) {
            ScoreVO scoreVO = (ScoreVO) obj;
            return name.equals(scoreVO.name);
        }
        return super.equals(obj);
    }
}
