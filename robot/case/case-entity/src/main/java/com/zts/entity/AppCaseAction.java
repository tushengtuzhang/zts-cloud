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
@Table(name="app_case_action")
public class AppCaseAction {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caseId", nullable = false)
    @JsonIgnore
    private AppCase appCase;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appCaseAction")
    private List<AppCaseActionTip> appCaseActionTipList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    @JsonIgnore
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "corpusTypeId")
    private CompCorpusType compCorpusType;
    private String corpusTypeName;

    private String name;
    private String value;

    private boolean bounded;
    private Integer required;
    private Integer needMatch;
    private Integer saveMatch;
    private Integer general;

    private Integer seq;


    /**
     * 如果是从当前进来的,考虑到不断往下匹配，该值应该会从一个action跳转到下一个action;
     */
    @Transient
    private boolean matchHere;

    /**
     * 作为场景动作匹配的值，输入值放到这里
     */
    @Transient
    private String matchValue;

    /**
     * 是否匹配
     */
    @Transient
    private boolean match;

    /**
     * 再次询问次数
     */
    @Transient
    private int askAgainTime=0;

    /**
     * app下的气泡
     */
    @Transient
    private List<String> bubbleList;

}
