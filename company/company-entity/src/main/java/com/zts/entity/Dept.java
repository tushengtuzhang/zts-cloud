package com.zts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
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
@EntityListeners(AuditingEntityListener.class)
public class Dept {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "dept-uuid", strategy = "uuid")
    @GeneratedValue(generator = "dept-uuid")
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    /**父组织*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pid")
    @JsonIgnore
    private Dept parent;

    /**子组织*/
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="pid")
    private List<Dept> children = new ArrayList<>();

    private Integer sort;

    @CreatedDate
    private Date createTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="createUserId",nullable = false)
    @CreatedBy
    private AdminUser createUser;

    @LastModifiedDate
    private Date updateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="updateUserId")
    @LastModifiedBy
    private AdminUser updateUser;

}
