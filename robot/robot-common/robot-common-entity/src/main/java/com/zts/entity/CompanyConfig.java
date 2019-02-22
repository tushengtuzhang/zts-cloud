package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author zhangtusheng
 */
@Entity
@Table(name="company_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompanyConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    private String variable;
    private String value;
    private Integer type;
    private Integer canUpdate;
    private Integer canDelete;
    private String description;
    private Integer sort;

}
