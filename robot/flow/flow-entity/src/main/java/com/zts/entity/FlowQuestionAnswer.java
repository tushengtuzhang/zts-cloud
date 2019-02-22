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
@Table(name = "flow_question_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FlowQuestionAnswer extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="flowId",nullable = false)
    @JsonIgnore
    private Flow flow;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="qaId",nullable = false)
    private QuestionAnswer questionAnswer;

    private Integer sort;


}
