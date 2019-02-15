package com.zts.criteria;


import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 条件构造器
 * 用于创建条件表达式
 * @author zhangtusheng
 */
public class Restrictions {

    /**
     * 等于
     * @param fieldName 属性名
     * @param value 属性值
     * @return SimpleExpression
     */
    public static SimpleExpression eq(String fieldName, Object value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.EQ);
    }

    /**
     * 不等于
     * @param fieldName 属性名
     * @param value 属性值
     * @return SimpleExpression
     */
    public static SimpleExpression ne(String fieldName, Object value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.NE);
    }

    /**
     * 模糊匹配
     * @param fieldName 属性名
     * @param value 属性值
     * @return SimpleExpression
     */
    public static SimpleExpression like(String fieldName, String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LIKE);
    }


    /**
     * 大于
     * @param fieldName 属性名
     * @param value value
     * @return SimpleExpression
     */
    public static SimpleExpression gt(String fieldName, Object value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.GT);
    }

    /**
     * 小于
     * @param fieldName 属性名
     * @param value 属性值
     * @return SimpleExpression
     */
    public static SimpleExpression lt(String fieldName, Object value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LT);
    }

    /**
     * 大于等于
     * @param fieldName 属性名
     * @param value
     * @return SimpleExpression
     */
    public static SimpleExpression lte(String fieldName, Object value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.GTE);
    }

    /**
     * 小于等于
     * @param fieldName 属性名
     * @param value
     * @return SimpleExpression
     */
    public static SimpleExpression gte(String fieldName, Object value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LTE);
    }

    /**
     * 并且
     * @param criterionList criterionList
     * @return LogicalExpression
     */
    public static LogicalExpression and(Criterion... criterionList){
        return new LogicalExpression(criterionList, Criterion.Operator.AND);
    }
    /**
     * 或者
     * @param criterionList criterionList
     * @return LogicalExpression
     */
    public static LogicalExpression or(Criterion... criterionList){
        return new LogicalExpression(criterionList, Criterion.Operator.OR);
    }
    /**
     * 包含于
     * @param fieldName 属性名
     * @param value 属性值
     * @return LogicalExpression
     */
    @SuppressWarnings("rawtypes")
    public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
        if(ignoreNull&&(value==null||value.isEmpty())){
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i=0;
        for(Object obj : value){
            ses[i]=new SimpleExpression(fieldName,obj, Criterion.Operator.EQ);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.OR);
    }
}