package com.zts.dao;

import com.zts.entity.CompanyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author zhangtusheng
 */
public interface CompanyUserDao extends IBaseDao<CompanyUser,Integer>{

    @Query("from CompanyUser cu where cu.identification = :identification")
    CompanyUser getByIdentification(@Param("identification") String identification);
}
