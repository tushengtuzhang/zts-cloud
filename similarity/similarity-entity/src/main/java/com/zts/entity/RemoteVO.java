package com.zts.entity;

import lombok.Data;

import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
public class RemoteVO {

    private String company_code;
    private String type;
    private List<TextData> text_data;
}
