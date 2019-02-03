package com.zts.dao;

import com.zts.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author zhangtusheng
 */
@NoRepositoryBean
public interface IBaseDao<T extends BaseEntity,ID extends Serializable> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

}