package com.zts.entity;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhangtusheng
 */
@Data
public class ReplyMatchCondition implements Serializable{

    @Expose
    private String actionId;

    /**
     * 或者实现gson的ExclusionStrategy进行排除过滤
     */
    @Expose
    private Integer corpusTypeId;

    @Expose
    private List<ConditionValue> valueList;

    /**
     * 因为已经存在match,只能用match2暂时判断条件是否匹配
     */
    private boolean match2;

}
