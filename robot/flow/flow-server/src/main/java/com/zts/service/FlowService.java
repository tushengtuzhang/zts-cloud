package com.zts.service;

import com.zts.dao.FlowDao;
import com.zts.dao.IBaseDao;
import com.zts.entity.Flow;
import com.zts.service.impl.BaseServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Service
public class FlowService extends BaseServiceImpl<Flow,Integer> {

    @Resource
    private FlowDao flowDao;

    @Override
    public IBaseDao<Flow, Integer> getBaseDao() {
        return flowDao;
    }

    @Cacheable(value = "getLowerFlow", key = "#flow.id")
    public List<Flow> getLowerFlow(Flow flow, List<Flow> flows) {
        if (flows == null) {
            flows = new ArrayList<>();
            flows.add(flow);
        }

        if(flow.getChildren()!=null&&flow.getChildren().size()>0){
            flows.addAll(flow.getChildren());
            for(Flow f:flow.getChildren()){
                getLowerFlow(f,flows);
            }
        }

        return flows;
    }
}
