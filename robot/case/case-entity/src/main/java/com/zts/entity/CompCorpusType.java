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
@Table(name="comp_corpus_type")
public class CompCorpusType extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;
    private String name;
    private String nature;
    private Integer sort;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "compCorpusType")
    private List<CompCorpus> compCorpusList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "compCorpusType")
    private List<CompCorpusLabel> compCorpusLabelList;
}
