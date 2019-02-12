package com.zts.entity;

import java.util.Objects;

/**
 * @author zhangtusheng
 */

public enum Status{
    /**
     * 删除
     */
    DELETE(0),
    /**
     * 激活
     */
    ACTIVE(1),
    /**
     * 锁定
     */
    LOCK(2),
    /**
     * 审批
     */
    APPROVE(3);


    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    /**
     * 获取枚举实例
     * @param value value
     * @return Status
     */
    public static Status fromValue(Integer value) {
        for (Status status : Status.values()) {
            if (Objects.equals(value, status.getValue())) {
                return status;
            }
        }
        throw new IllegalArgumentException();
    }
}
