package com.zts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="appId",nullable = false)
    @JsonIgnore
    private App app;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appCase")
    private List<AppCaseQuestion> caseQuestionList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appCase")
    List<AppCaseAction> appCaseActionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appCase")
    List<AppCaseReply> appCaseReplyList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_case_relation", joinColumns = {
            @JoinColumn(name = "caseId", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "relationCaseId", nullable = false, updatable = false) })
    List<AppCase> appCaseRelationList;

    private Integer companyServiceId;
    private Integer originalServiceId;

    private String replyType;
    //来源
    private String ref;
    private Integer fileBaseId;

    @Transient
    private String firstQuestion="";

}
