package com.zts.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 *
 * 用户画像
 * @author zhangts
 */

@Entity
@Table(name = "persona")

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Persona extends BaseEntity{


    @Transient
    private String redisId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    /**
     * 对应 company_user 的identification
     * 想把userId换成identification
     */
    @Column(name = "userId")
    private String userId;

    /**
     * 对应company_user 的用户名
     */
    @Transient
    private String userName;

    private Integer caseId;

    /**
     * 是否受限
     */
    private boolean bounded;

    /**
     * 标签分类，如 职级id
     */
    private String labelType;

    /**
     * 标签名称 职级
     */
    private String labelName;

    /**
     * 标签键，如zhiji
     */
    private String labelKey;

    /**
     * 标签值，如P8
     */
    private String labelValue;

    /**
     * 上次使用时间
     */
    private Date lastUseTime;


    /** 需要删除的Id*/
    @Transient
    private List<Integer> deleteIds;

    /** persona集合*/
    @Transient
    private List<Persona> personaList;

    /** 需要删除的Id */
    @Transient
    private List<String> deleteUserIds;


    /** 需要添加或修改的Id */
    @Transient
    private List<String> addUserIds;

}
