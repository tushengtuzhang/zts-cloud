package com.zts.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@AllArgsConstructor
public class CaseActionMessage implements Message{

    private Integer caseId;
    private String lifeCycle;

    /**
     * 动作提示语
     */
    private String actionTip;
    /**
     * 气泡
     */
    private List<String> bubbleList;

}
