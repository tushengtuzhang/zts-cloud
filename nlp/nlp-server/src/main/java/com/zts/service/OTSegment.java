package com.zts.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Other.AhoCorasickDoubleArrayTrieSegment;
import com.hankcs.hanlp.seg.common.Term;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lycan on 2017/9/13.
 */
public class OTSegment extends AhoCorasickDoubleArrayTrieSegment implements Serializable {

    public OTSegment(String... dictionaryPath) {
        loadDictionary(dictionaryPath);
    }

    public List<Term> segByCustomDic(String text) {

        List<Term> seg = seg(text); //自定义公司词典的分词
        List<Term> otSegment=new ArrayList<>();

        StringBuilder appendString = new StringBuilder();

        //为保证拆词的顺序及拆词准确，其它专名的词汇要合并以后再由hanlp进行拆词
        for(Term term:seg){
            if(term.nature == Nature.nz){ //如果是其他专名，并到appendString字符串中
                appendString.append(term.word);
            }else{
                if(appendString.length()>0){
                    otSegment.addAll(HanLP.segment(appendString.toString()));
                    appendString=new StringBuilder();
                }
                otSegment.add(term);
            }
        }
        if(appendString.length()>0){
            otSegment.addAll(HanLP.segment(appendString.toString()));
        }


        return otSegment;
    }
}
