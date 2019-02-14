package com.zts.controller;

import com.zts.entity.CorpusType;
import com.zts.service.CorpusTypeService;
import com.zts.vo.ReturnVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping("/compCorpusType")
public class CorpusTypeController {

    @Resource
    private CorpusTypeService corpusTypeService;

    @RequestMapping(value = "/page", name = "分页")
    @ResponseBody
    public ReturnVO<?> list(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "15") int size, @RequestParam(defaultValue = "ASC")Sort.Direction direction, @RequestParam(defaultValue = "id") String orderBy) {

        PageRequest pageRequest=PageRequest.of(pageNumber-1,size,new Sort(direction,orderBy));
        Page<CorpusType> page = corpusTypeService.findAll(pageRequest);
        ReturnVO returnVO=new ReturnVO();
        returnVO.setTotal(page.getTotalElements());
        returnVO.setData(page.getContent());

        return returnVO;
    }

}
