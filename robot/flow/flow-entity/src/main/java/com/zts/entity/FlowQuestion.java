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
@Table(name = "flow_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FlowQuestion extends BaseEntity{

    private String question;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="flowId",nullable = false)
    @JsonIgnore
    private Flow flow;
}
