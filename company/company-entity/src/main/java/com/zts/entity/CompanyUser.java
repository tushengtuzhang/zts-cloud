package com.zts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangtusheng
 */
@Entity
@Table(name = "company_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CompanyUser extends BaseEntity {

    private String username;
    /**
     * 所属公司ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String cellphone;
    /**
     * 人脸识别标识
     */
    private String faceToken;
    /**
     * 所在部门
     */
    private String department;
    /**
     * 岗位
     */
    private String job;
    /**
     * 备注
     */
    private String remark;
    /**
     * 微信帐号
     */
    private String weChatId;
    /**
     * 微信昵称
     */
    private String weChatName;
    /**
     * 微信头像地址
     */
    private String weChatLogo;
    /**
     * 状态: -1=已注销, 0=停用, 1=启用
     * {@link Status}
     */
    private Integer status;
    /**
     * 修改时间
     */
    private Long modifyTime;
    /**
     * 入职年月
     */
    private Long entryTime;
    /**
     * 工龄
     */
    private Integer workAge;
    /**
     * 学历
     */
    private String education;
    /**
     * 平台侧用户头像id
     */
    private String avatar;
    private Date avatarTime;
    /**
     * 性别
     * {@link Sex}
     */
    private Integer sex;
    /**
     * 生日
     */
    private Long birth;
    /**
     * 登录密码
     */
    private String pwd;
    /**
     * 盐
     */
    private String salt;
    /**
     * 用户类型（6人工客服）
     */
    private String userType;
    /**
     * 上次登录时间
     */
    private Long lastLoginTime;
    /**
     * 上次登录地点
     */
    private String lastLoginAdd;
    /**
     * 上次修改密码时间
     */
    private Long lastUpdatePwdTime;
    /**
     * 限制IP登录：0-否，1-是
     */
    private Integer ipLimit;
    /**
     * 限制IP登录的值
     */
    private String ipLimitValue;
    private Long articlesRefreshTime;
    /**
     * 用户唯一标识，为了。。支持匿名登录 的字段
     */
    private String identification;
    /**
     * 是否匿名用户
     * <p>
     * 这个字段这个不需要持久化
     */
    @Transient
    private boolean anonymous;

    private String nickname;
    private String idnbr;

    @Transient
    @JsonIgnore
    private Integer offset;
    @Transient
    @JsonIgnore
    private Integer limit;
}
