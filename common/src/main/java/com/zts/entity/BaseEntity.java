package com.zts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangtusheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    @Convert(converter = StatusConverter.class)
    private Status status=Status.ACTIVE;

    @Transient
    @JsonIgnore
    private Date startTime;

    @Transient
    @JsonIgnore
    private Date endTime;

}
