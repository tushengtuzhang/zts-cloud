package com.zts.entity;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangtusheng
 */
@Data
public class ConditionValue implements Serializable {
    /**
     * 0临时语料，1语料（词库） 2、标签
     */
    @Expose
    private Integer type;
    @Expose
    private String value;
}
