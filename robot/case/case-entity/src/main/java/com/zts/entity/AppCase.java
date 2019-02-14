package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "app_case")

public class AppCase extends BaseEntity{

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appCase")
    private List<AppCaseQuestion> caseQuestionList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appCase")
    List<CaseAction> caseActionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appCase")
    List<CaseReply> caseReplyList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_case_relation", catalog = "zts_cloud", joinColumns = {
            @JoinColumn(name = "caseId", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "relationCaseId", nullable = false, updatable = false) })
    List<AppCase> appCaseRelationList;

}
