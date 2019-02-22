package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Company extends BaseEntity{

    /** 公司名称*/
    private String name;

    /** 所属行业*/
    private String industry;

    /** 所在省份*/
    private String province;

    /** 所在城市*/
    private String city;

    /** 所在区县*/
    private String district;

    /**详细地址*/
    private String address;

    /** 联系人*/
    private String contacts;

    /**联系电话*/
    private String telephone;

    /**简介*/
    private String brief;

    /**LOGO*/
    private String logoUrl;

    /**开通时间*/
    private Date openTime;

    /** 到期时间*/
    private Date dueTime;

    /** 企业识别码*/
    private String code;

    /** 允许制度数量*/
    private Integer allowInstitutionQty;

    /**状态: 0=停用, 1=启用*/
    private Boolean status;

    /** 是否无限制  1：是 0：否*/
    private Boolean unlimited;

    /** 有效标志 0正常  -1 无效或删除*/
    private Integer availableTag;

    /** 是否开放注册: 0=否, 1=是*/
    private Boolean openRegStatus;

    /**公司类型 0=普通企业，1=行业企业*/
    private Integer companyType;

    /**记事本*/
    private String noteBook;

    /**外部应用秘钥*/
    private String extendAppSecret;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "company")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CompanyIndustryRelation> companyIndustryRelationList;


}