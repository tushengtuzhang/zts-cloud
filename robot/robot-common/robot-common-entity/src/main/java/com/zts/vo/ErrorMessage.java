package com.zts.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhangtusheng
 */
@Data
@AllArgsConstructor
public class ErrorMessage implements Message{

    private String text;

}
