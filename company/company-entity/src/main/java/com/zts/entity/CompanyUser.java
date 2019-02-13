package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhangtusheng
 */
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUser extends BaseEntity {

    private String userName;
}
