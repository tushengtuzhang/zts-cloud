package com.zts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author zhangtusheng
 */
@Entity
@Table(name = "flow_case")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FlowCase extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="flowId",nullable = false)
    @JsonIgnore
    private Flow flow;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="caseId",nullable = false)
    @JsonIgnore
    private AppCase appCase;

    private Integer sort;
}
