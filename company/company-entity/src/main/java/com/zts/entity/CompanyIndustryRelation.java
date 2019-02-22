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
@Table(name = "company_industry_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CompanyIndustryRelation extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    /** 被关联公司ID.（行业企业） */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="refCompanyId",nullable = false)
    private Company refCompany;

    /** 排序（优先级），越小排越前. */
    private Integer sort;
}
