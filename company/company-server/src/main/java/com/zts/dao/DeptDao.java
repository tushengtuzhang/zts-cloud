package com.zts.dao;

import com.zts.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangtusheng
 */
public interface DeptDao extends JpaRepository<Dept, String> {

}
