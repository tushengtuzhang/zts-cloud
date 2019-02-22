package com.zts.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangtusheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action implements Serializable{

    private String actionId;
    private String key;
    private String value;

    @Override
    public int hashCode(){
        return actionId.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Action){
            Action appCase=(Action)obj;
            return actionId.equals(appCase.actionId);
        }
        return super.equals(obj);
    }
}
