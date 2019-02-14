package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangtusheng
 */
@Entity
@Table(name = "admin_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdminUser extends BaseEntity{

    private String userName;
    private String pwd;
    private String salt;

    private String realName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    private Integer userType;

    @Convert(converter = StatusConverter.class)
    private Status state=Status.ACTIVE;

    private Integer selfAdmin;
    private Integer viewOnlyAdmin;

    private String idCard;
    private String loginPhone;

    private Date loginTime;
    private Date lastLoginTime;

    private String loginIp;
    private String lastLoginIp;

    private String contact;
    private String email;

    private Date dueTime;
    /**
     * 是否无限制：0-否，1-是
     */
    private Integer unlimited;

    /**
     * 限制IP登录：0-否，1-是
     */
    @ColumnDefault("0")
    private Integer ipLimit;
    private String ipLimitValue;

    /**
     * 审批 0：未审批 1：审批不通过 2：审批通过
     */
    @ColumnDefault("2")
    private String approve;

}
