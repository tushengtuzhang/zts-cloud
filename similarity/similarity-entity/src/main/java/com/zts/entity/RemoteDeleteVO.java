package com.zts.entity;

import lombok.Data;

import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
public class RemoteDeleteVO {

    private String company_code;
    private String type;
    private List<String> delete_id;

}
