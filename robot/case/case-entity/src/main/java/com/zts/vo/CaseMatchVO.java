package com.zts.vo;

import com.zts.entity.AppCase;
import com.zts.entity.AppCaseQuestion;
import com.zts.entity.AppCaseAction;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtusheng
 */
@Data
public class CaseMatchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appCaseFirstQuestion;
    private AppCaseQuestion appCaseQuestion;

    private AppCase appCase;

    private List<String> bubbleList;

    private String ask;

    /**
     * 回复中点击的下划线对应的动作
     */
    private AppCaseAction replyAction;
    /**
     * 是否来自动作
     */
    private boolean fromAction;

    /**
     * 用于记录命中统计matched_log的id
     */
    private Integer matchedId;

    private Map<String,String> matchedActionMap;

    private List<AppCaseQuestion> relationQuestionList;

    /**
     * 如果没有找到受限的用户画像，则置为true,用于出默认回复
     */
    private Boolean noBounded=false;
}
