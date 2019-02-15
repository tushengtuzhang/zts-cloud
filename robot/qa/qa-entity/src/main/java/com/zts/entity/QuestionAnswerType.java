package com.zts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "question_answer_type")
public class QuestionAnswerType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "qa-type-uuid", strategy = "uuid")
    @GeneratedValue(generator = "qa-type-uuid")
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="parentId")
    @JsonIgnore
    private QuestionAnswerType parent;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="parentId")
    private List<QuestionAnswerType> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="deptId",nullable = false)
    private Dept dept;

    @Transient
    private boolean readOnly;


}
