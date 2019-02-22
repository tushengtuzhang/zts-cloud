package com.zts.service;

import com.zts.criteria.Criteria;
import com.zts.criteria.Restrictions;
import com.zts.entity.CompCorpus;
import com.zts.entity.CompCorpusLabel;
import com.zts.entity.CompCorpusType;
import com.zts.feign.NlpFeign;
import org.apache.http.util.TextUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lycan on 2017/9/18.
 */
@Service
public class OTDictionaryService {

    @Resource
    private CorpusTypeService corpusTypeService;

    @Resource
    private NlpFeign nlpFeign;


    public void synDictionary(Integer companyId) {

        StringBuilder sb = new StringBuilder();

        Map<String, Object> param = new HashMap<>();
        param.put("companyId", companyId);
        param.put("isNeededNUllNature", false);

        Criteria<CompCorpusType> criteria=new Criteria<>();
        criteria.add(Restrictions.eq("company.id",companyId));
        criteria.add(Restrictions.ne("nature",null));

        Sort sort=new Sort(Sort.Direction.DESC,"id");

        List<CompCorpusType> compCorpusTypeList = corpusTypeService.findList(criteria, sort);


        for (CompCorpusType compCorpusType : compCorpusTypeList) {
            if (!TextUtils.isEmpty(compCorpusType.getName().replaceAll(" ", "")
                    .replaceAll("\\s*", "")
                    .replaceAll(" +", "").trim())) {
                sb.append(getLineContent(compCorpusType.getName(), compCorpusType.getNature()));
            }
            for(CompCorpus compCorpus : compCorpusType.getCompCorpusList()){
                if (!TextUtils.isEmpty(compCorpus.getName().replaceAll(" ", "")
                        .replaceAll("\\s*", "")
                        .replaceAll(" +", "").trim())) {
                    sb.append(getLineContent(compCorpus.getName(), compCorpusType.getNature()));
                }
            }
            for(CompCorpusLabel compCorpusLabel : compCorpusType.getCompCorpusLabelList()){
                if (!TextUtils.isEmpty(compCorpusLabel.getLabelName().replaceAll(" ", "")
                        .replaceAll("\\s*", "")
                        .replaceAll(" +", "").trim())) {
                    sb.append(getLineContent(compCorpusLabel.getLabelName(), compCorpusType.getNature()));
                }
            }
        }

        nlpFeign.updateDictionary(companyId,sb.toString());

    }

    private String getLineContent(String name, String nature) {
        return name.trim()
                .replaceAll(" ", "")
                .replaceAll("\\s*", "")
                .replaceAll(" +", "") +
                " " +
                nature +
                " " +
                10000 +
                "\n";
    }

}
