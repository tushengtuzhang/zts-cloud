package com.zts.controller;

import com.zts.entity.AppCase;
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
public class AppCaseController {

    @Resource
    private AppCaseService appCaseService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public R list(){
        Map<String,Object> map=new HashMap<>();

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
    public R save(AppCase appCase){

        appCase.setCreateTime(new Date());

        Map<String,Object> map=new HashMap<>();
        appCaseService.save(appCase);
        map.put("entity",appCase);
        return R.ok(map);
    }

    @RequestMapping(value = "update")
    @ResponseBody
    @Transactional
    public R update(AppCase appCase){

        appCase.setUpdateTime(new Date());

        Map<String,Object> map=new HashMap<>();
        map.put("entity", appCaseService.update(appCase));

        return R.ok(map);
    }

    @RequestMapping(value="delete")
    @ResponseBody
    @Transactional
    @SuppressWarnings("unchecked")
    public R delete(Long id){
        appCaseService.delete(id);
        return R.error();
    }
}
