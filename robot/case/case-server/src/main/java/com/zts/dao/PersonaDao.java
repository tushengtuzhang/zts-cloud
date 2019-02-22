package com.zts.dao;

import com.zts.entity.Persona;
import org.springframework.data.jpa.repository.Query;

/**
 * @author zhangtusheng
 */
public interface PersonaDao extends IBaseDao<Persona,Integer>{

    @Query("delete from Persona where company.id=:companyId and bounded=1")
    void deleteBoundedListByCompanyId(Integer companyId);

    @Query("select p,cu.username  from Persona p left join CompanyUser cu on p.userId=cu.identification where p.id= :id")
    Persona selectById(Integer id);

}
