package com.zts.controller;

import com.zts.criteria.Criteria;
import com.zts.criteria.Restrictions;
import com.zts.entity.Flow;
import com.zts.service.FlowService;
import com.zts.vo.ReturnVO;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/flow")
public class FlowController {

    @Resource
    private FlowService flowService;

    @RequestMapping(value = "/getFlowList")
    @ResponseBody
    public List<Flow> getFlowList() {
        //从token中获取用户和公司
        Integer userId=1;
        Integer companyId=1;
        if (companyId == 0){
            return null;
        }

        Criteria<Flow> criteria=new Criteria<>();
        criteria.add(Restrictions.eq("company.id",1));
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        return flowService.findList(criteria,sort);
    }



    @RequestMapping(value = "/doSaveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ReturnVO save(Flow flow){
        flowService.save(flow);
        return ReturnVO.OK(flow);
    }


    @RequestMapping(value = "/getListByCompanyId")
    @ResponseBody
    public List<Flow> getListByCompanyId(Integer companyId){
        Criteria<Flow> criteria=new Criteria<>();
        criteria.add(Restrictions.eq("company.id",companyId));
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        return flowService.findList(criteria,sort);
    }

    @RequestMapping(value = "/get/{id}")
    @ResponseBody
    public Flow get(@PathVariable Integer id){
        return flowService.find(id);
    }
}
