package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimilarityVO {

    private String company_code;
    /**
     * @Link RemoteConstant type
     * 如果为空，则查所有
     */
    private String type;
    private Integer num_sim;
    private String infer_text;

    /**
     * 如果ids不为空，则type失效，只在ids里面进行查找
     */
    private List<String> ids;

}
