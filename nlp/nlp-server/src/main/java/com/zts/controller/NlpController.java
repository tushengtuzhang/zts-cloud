package com.zts.controller;

import com.hankcs.hanlp.seg.common.Term;
import com.zts.service.FileOperator;
import com.zts.service.OTNlp;
import com.zts.service.OTTokenizer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangtusheng
 */
@RestController
@RequestMapping(value = "/nlp")
public class NlpController {

    @RequestMapping(value="/updateDictionary")
    public void update(Integer companyId,String text){
        FileOperator.reWriteFile(OTNlp.getCompanyDic(companyId), text);
        OTTokenizer.refreshDic(companyId);
    }

    @RequestMapping(value = "/segment")
    public List<Term> segment(Integer companyId,String text){
        List<Term> segment = OTNlp.segment(companyId, text);
        return segment;
    }
}
