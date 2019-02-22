package com.zts.dao;

import com.zts.entity.MatchedLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zhangtusheng
 */
public interface MatchedLogDao extends JpaRepository<MatchedLog, Integer>, JpaSpecificationExecutor<MatchedLog> {

    @Query("select distinct companyId, userId from MatchedLog where createTime > :#{min.createTime}")
    List<MatchedLog> selectDistinctUser(MatchedLog min);

    @Query(nativeQuery = true,value = "select matchedType, matchedId, matchedName, createTime from matched_log " +
            "where companyId = :#{whose.companyId} and userId = :#{whose.userId} and createTime > :#{whose.createTime} order by createTime")
    List<MatchedLog> selectMine(MatchedLog whose);
}
