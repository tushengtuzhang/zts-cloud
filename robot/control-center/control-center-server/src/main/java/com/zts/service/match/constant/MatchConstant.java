package com.zts.service.match.constant;

public interface MatchConstant {

    String CASE_MATCH="case_match_";
    String LAST_CASE="last_case_";

    String NOT_NEED_MATCH_ACTION="notNeedMatchAction";

    String FLOW_MATCH="flow_match_";
    String FLOW_LAST_ASK="flow_last_ask_";

    String THIRD_PARTY_SERVICE_VO ="third_party_service_vo_";

    String BOUNDED_REPLY="您好，该场景受限，您无权限或查不到您的画像信息。";

    Double CASE_FULL_MATCH_SCORE = 1.0d;
    Double CASE_MATCH_MAX_SCORE = 0.9d; //场景默认匹配阈值
    Double CASE_MATCH_MIN_SCORE = 0.5d; //场景默认最小匹配阈值（则0.5-0.9的列表， >0.9 则直接出场景）

    Double FLOW_MATCH_MAX_SCORE = 0.7d;
    Double FLOW_MATCH_MIN_SCORE = 0.5d;

    Integer ACTION_ASK_AGAIN=1;

    Double QUESTION_ANSWER_MATCH_MAX_SCORE = 0.9d; //快问快答默认匹配阈值
    Double QUESTION_ANSWER_MATCH_MIN_SCORE = 0.7d;




}
