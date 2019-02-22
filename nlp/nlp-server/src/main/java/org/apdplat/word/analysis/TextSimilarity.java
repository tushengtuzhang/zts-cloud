/**
 *
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.apdplat.word.analysis;

import com.hankcs.hanlp.seg.common.MyTerm;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文本相似度
 * @author 杨尚川
 */
public abstract class TextSimilarity {



    /**
     * 文本1和文本2的相似度分值
     * @param text1 文本1
     * @param text2 文本2
     * @return 相似度分值
     */
    public double similarScore(String text1, String text2) {
        if(text1 == null || text2 == null){
            //只要有一个文本为null，规定相似度分值为0，表示完全不相等
            return 0.0;
        }
        //分词
        List<MyTerm> words1 = seg(text1);
        List<MyTerm> words2 = seg(text2);
        //计算相似度分值
        return similarScore(words1, words2);
    }

    /**
     * 词列表1和词列表2的相似度分值
     * @param words1 词列表1
     * @param words2 词列表2
     * @return 相似度分值
     */
    public double similarScore(List<MyTerm> words1, List<MyTerm> words2) {
        if(words1 == null || words2 == null){
            //只要有一个文本为null，规定相似度分值为0，表示完全不相等
            return 0.0;
        }
        if(words1.isEmpty() && words2.isEmpty()){
            //如果两个文本都为空，规定相似度分值为1，表示完全相等
            return 1.0;
        }
        if(words1.isEmpty() || words2.isEmpty()){
            //如果一个文本为空，另一个不为空，规定相似度分值为0，表示完全不相等
            return 0.0;
        }

        double score = scoreImpl(words1, words2);
        score = (int)(score*1000000+0.5)/(double)1000000;
        return score;
    }

    /**
     * 计算相似度分值
     * @param words1 词列表1
     * @param words2 词列表2
     * @return 相似度分值
     */
    protected abstract double scoreImpl(List<MyTerm> words1, List<MyTerm> words2);

    /**
     * 对文本进行分词
     * @param text 文本
     * @return 分词结果
     */
    protected List<MyTerm> seg(String text){
        if(text == null){
            return Collections.emptyList();
        }

        List<Term> segment=NLPTokenizer.segment(text);
        //List<Term> segments=SpeedTokenizer.segment(text);
        List<MyTerm> words=new ArrayList<>();
        for(Term term:segment){
            MyTerm myTerm=new MyTerm(term.word,term.nature);
            words.add(myTerm);
        }
        return words;
    }

    /**
     * 如果没有指定权重，则默认使用词频来标注词的权重
     * 词频数据怎么来？
     * 一个词在词列表1中出现了几次，它在词列表1中的权重就是几
     * 一个词在词列表2中出现了几次，它在词列表2中的权重就是几
     * 标注好的权重存储在Word类的weight字段中
     * @param words1 词列表1
     * @param words2 词列表2
     */
    protected void taggingWeightWithWordFrequency(List<MyTerm> words1, List<MyTerm> words2){
        if(words1.get(0).frequency != null || words2.get(0).frequency != null){
            return;
        }
        //词频统计
        Map<String, AtomicInteger> frequency1 = frequency(words1);
        Map<String, AtomicInteger> frequency2 = frequency(words2);

        //权重标注
        words1.parallelStream().forEach(word->{
            word.frequency=frequency1.get(word.word).floatValue();
        });
        words2.parallelStream().forEach(word->{
            word.frequency=frequency2.get(word.word).floatValue();
        });
    }


    /**
     * 构造权重快速搜索容器
     * @param words 词列表
     * @return Map
     */
    protected Map<String, Float> toFastSearchMap(List<MyTerm> words){
        Map<String, Float> weights = new ConcurrentHashMap<>();
        if(words == null){
            return weights;
        }
        words.parallelStream().forEach(word -> {

            weights.put(word.word,word.frequency);
        });
        return weights;
    }

    /**
     * 统计词频
     * @param words 词列表
     * @return 词频统计结果
     */
    private Map<String, AtomicInteger> frequency(List<MyTerm> words){
        Map<String, AtomicInteger> frequency =new HashMap<>();
        words.forEach(word->{
            frequency.computeIfAbsent(word.word, k -> new AtomicInteger()).incrementAndGet();
        });
        return frequency;
    }

}
