package com.zts.entity;

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
@Table(name = "question_answer")
public class QuestionAnswer extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    /**
     * 快问快答，问题，这里会用换行来分隔问题
     */
    private String question;

    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="type",nullable = false)
    private QuestionAnswerType questionAnswerType;


}
