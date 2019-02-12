package com.zts.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author zhangtusheng
 */
@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status,Integer> {
    @Override
    public Integer convertToDatabaseColumn(Status status) {
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(Integer integer) {
        return Status.fromValue(integer);
    }
}
