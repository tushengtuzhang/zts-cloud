package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Entity
@Table(name = "admin_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdminRole extends BaseEntity{

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "admin_role_dept", joinColumns = {
            @JoinColumn(name = "roleId", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "deptId", nullable = false, updatable = false) })
    private List<Dept> deptList = new ArrayList<>();

}
