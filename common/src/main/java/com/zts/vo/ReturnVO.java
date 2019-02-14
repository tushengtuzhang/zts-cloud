package com.zts.vo;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 返回结果.
 *
 * @author zhangtusheng
 * @param <T> 返回数据类型
 * @version 1.0.0
 * @since JDK 1.4.2.6
 */
public class ReturnVO<T> implements Serializable {

    /**
     * 返回成功代码, 0-成功.
     */
    public static int CODE_SUCCESS = 0;

    /**
     * 返回错误代码, 1-错误.
     */
    public static int CODE_ERROR = 1;

    /**
     * 0-成功, 1-失败.
     */
    protected int code;

    /**
     * 返回信息, 错误内容.
     */
    protected String msg = "";

    /**
     * 返回的对像, 正确时返回的结果.
     */
    protected T data;

    /**
     * 总记录数, 用于分页.
     */
    protected Long total;

    public ReturnVO() {
    }

    public ReturnVO(int code) {
        this.code = code;
    }

    public static <T> ReturnVO<T> OK(T data) {
        ReturnVO<T> dto = new ReturnVO<T>(CODE_SUCCESS);
        dto.setData(data);
        return dto;
    }

    public static <T> ReturnVO<T> OK(String msg, T data) {
        ReturnVO<T> dto = new ReturnVO<T>(CODE_SUCCESS);
        dto.setMsg(msg);
        dto.setData(data);
        return dto;
    }

    public static <T> ReturnVO<T> NG(String msg) {
        ReturnVO<T> dto = new ReturnVO<T>(CODE_ERROR);
        dto.setMsg(msg);
        return dto;
    }

    /**
     * 构建分页
     *
     * @param page
     * @param <T>
     * @return
     */
    public static <T> ReturnVO<List<T>> page(Page<T> page) {
        List<T> list = page.getContent();
        ReturnVO<List<T>> returnVO = ReturnVO.OK(list);
        returnVO.setTotal(page.getTotalElements());
        return returnVO;
    }

    public void success(T data) {
        this.code = CODE_SUCCESS;
        this.data = data;
    }

    public void success(String msg, T data) {
        this.code = CODE_SUCCESS;
        this.msg = msg;
        this.data = data;
    }

    public void error(String msg) {
        this.code = CODE_ERROR;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
