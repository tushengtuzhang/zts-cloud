package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name="comp_corpus_label")
public class CorpusLabel extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="corpusId",nullable = false)
    private CorpusType corpusType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    private String labelName;
    private Integer sort;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comp_corpus_label_relation",joinColumns = {
            @JoinColumn(name = "corpusLabelId", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "corpusId", nullable = false, updatable = false) })
    private List<Corpus> corpusList;
}
