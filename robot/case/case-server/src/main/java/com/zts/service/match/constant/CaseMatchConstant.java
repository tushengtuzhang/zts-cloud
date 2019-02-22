package com.zts.service.match.constant;

/**
 * @author zhangtusheng
 */
public interface CaseMatchConstant {

    String CASE_MATCH="case_match_";
    String LAST_CASE="last_case_";

    Double CASE_FULL_MATCH_SCORE = 1.0d;
    /** 场景默认匹配阈值 */
    Double CASE_MATCH_MAX_SCORE = 0.9d;
    /**场景默认最小匹配阈值（则0.5-0.9的列表， >0.9 则直接出场景）*/
    Double CASE_MATCH_MIN_SCORE = 0.5d;

    String THIRD_PARTY_SERVICE_VO ="third_party_service_vo_";

    Integer ACTION_ASK_AGAIN=1;

    String BOUNDED_REPLY="您好，该场景受限，您无权限或查不到您的画像信息。";
}
