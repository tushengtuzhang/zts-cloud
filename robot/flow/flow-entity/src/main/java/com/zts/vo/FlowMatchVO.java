package com.zts.vo;

import com.zts.entity.Flow;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangts
 * @date 2017-08-03
 */
@Data
public class FlowMatchVO implements Serializable {
    /**当前流程树节点*/
    private Flow currentFlow;

}
