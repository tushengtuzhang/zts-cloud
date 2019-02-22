package com.zts.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 普通文本形式的消息
 *
 * @author zipeng
 * @date 2018/11/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextMessage implements Message {
    private String text;
}
