package com.zts.service;

import com.hankcs.hanlp.seg.common.MyTerm;
import com.hankcs.hanlp.seg.common.Term;
import org.apdplat.word.analysis.CosineTextSimilarity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lycan on 2017/9/12.
 */
public class OTTextSimilarity extends CosineTextSimilarity {

    @Override
    protected List<MyTerm> seg(String text) {
        return super.seg(text);
    }

    @Override
    public double similarScore(String text1, String text2) {
        return super.similarScore(text1, text2);
    }

    @Override
    public double similarScore(List<MyTerm> words1, List<MyTerm> words2) {
        return super.similarScore(words1, words2);
    }

    public double similarScore(int companyId, String text1, String text2) {
        List<MyTerm> words1 ;
        List<MyTerm> words2 ;
        if (!new File(OTNlp.getCompanyDic(companyId)).exists()) {
            words1 = seg(text1);
            words2 = seg(text2);
        } else {
            words1 = seg(text1, OTNlp.getCompanyDic(companyId));
            words2 = seg(text2, OTNlp.getCompanyDic(companyId));
        }

        return super.similarScore(words1, words2);
    }

    /**
     * 对文本进行分词
     *
     * @param text 文本
     * @return 分词结果
     */
    public List<MyTerm> seg(String text, String pathArray) {
        if (text == null) {
            return Collections.emptyList();
        }
        List<Term> segment = OTTokenizer.getInstance().segment(text, pathArray);
        List<MyTerm> words = new ArrayList<>();
        for (Term term : segment) {
            MyTerm myTerm = new MyTerm(term.word, term.nature);
            words.add(myTerm);
        }

        return words;
    }
}
