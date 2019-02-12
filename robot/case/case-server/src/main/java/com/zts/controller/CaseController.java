package com.zts.controller;

import com.zts.entity.AppCase;
import com.zts.entity.Company;
import com.zts.feign.CompanyFeign;
import com.zts.service.AppCaseService;
import com.zts.vo.R;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/appCase")
public class CaseController {

    @Resource
    private AppCaseService appCaseService;

    @Resource
    private CompanyFeign companyFeign;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public R list(){
        Map<String,Object> map=new HashMap<>(1);

        map.put("list", appCaseService.findAll());

        return R.ok(map);
    }

    @RequestMapping(value = "/page")
    @ResponseBody
    public R page(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "15") int size, @RequestParam(defaultValue = "ASC") Sort.Direction direction, @RequestParam(defaultValue = "id") String orderBy){

        PageRequest pageRequest= PageRequest.of(pageNumber-1,size,new Sort(direction,orderBy));

        Map<String,Object> map=new HashMap<>();
        map.put("list", appCaseService.findAll(pageRequest));

        return R.ok(map);

    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public R save(AppCase aAppCase){

        aAppCase.setCreateTime(new Date());

        Map<String,Object> map=new HashMap<>(1);
        appCaseService.save(aAppCase);
        map.put("entity", aAppCase);
        return R.ok(map);
    }

    @RequestMapping(value = "update")
    @ResponseBody
    @Transactional(rollbackOn = Exception.class)
    public R update(AppCase aAppCase){

        aAppCase.setUpdateTime(new Date());

        Map<String,Object> map=new HashMap<>(1);
        map.put("entity", appCaseService.update(aAppCase));

        return R.ok(map);
    }

    @RequestMapping(value="delete")
    @ResponseBody
    @Transactional(rollbackOn = Exception.class)
    @SuppressWarnings("unchecked")
    public R delete(Long id){
        appCaseService.delete(id);
        return R.error();
    }
    @GetMapping("/company/{companyId}")
    @ResponseBody
    public Company get(@PathVariable Long companyId){
        Company company= companyFeign.get(companyId);
        return company;
    }
}
