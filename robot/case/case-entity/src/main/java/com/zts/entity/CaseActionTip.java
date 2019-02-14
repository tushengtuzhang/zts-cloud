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
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "app_case_action_tips")
public class CaseActionTip extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caseId", nullable = false)
    @JsonIgnore
    private AppCase appCase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actionId", nullable = false)
    @JsonIgnore
    private CaseAction caseAction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    @JsonIgnore
    private Company company;

    private String tips;

    private Integer seq;
}
