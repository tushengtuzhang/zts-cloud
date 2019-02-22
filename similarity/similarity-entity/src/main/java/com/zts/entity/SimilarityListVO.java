package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@AllArgsConstructor
public class SimilarityListVO {

    private String test_text;

    private List<String> matched_data;

}
