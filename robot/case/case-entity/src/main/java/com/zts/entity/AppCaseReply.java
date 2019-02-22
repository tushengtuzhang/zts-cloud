package com.zts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zts.vo.ReplyMatchCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name="app_case_reply")
public class AppCaseReply extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caseId", nullable = false)
    @JsonIgnore
    private AppCase appCase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="companyId",nullable = false)
    @JsonIgnore
    private Company company;

    private String matchCondition;

    /**
     * 组合条件
     */

    @Transient
    private List<List<ReplyMatchCondition>> replyMatchConditionListCombination;

    @Transient
    private boolean match;

    private Integer isDefaultReply;

    private String reply;

    private Integer seq;

    public List<List<ReplyMatchCondition>> getReplyMatchConditionListCombination() {
        if(replyMatchConditionListCombination==null&&matchCondition!=null&&matchCondition!="[]"&&matchCondition!="[[]]"){
            replyMatchConditionListCombination=new Gson().fromJson(matchCondition,new TypeToken<List<List<ReplyMatchCondition>>>(){}.getType());
        }
        return replyMatchConditionListCombination;
    }
}
