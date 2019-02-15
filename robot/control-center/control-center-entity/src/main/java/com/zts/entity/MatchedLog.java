package com.zts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangtusheng
 */
@Entity
@Table(name = "matched_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MatchedLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @OrderBy("DESC")
    private Integer id;
    private Integer companyId;
    private String companyName;
    private String userId;
    private String userName;
    private Date createTime;
    private String input;
    private String matchedType;
    private String matchedId;
    private String matchedName;
    private Boolean liked;
    private Date likeTime;
    private String feedback;
    private Integer msglgid;

    @Transient
    private String xaxis;
    @Transient
    private Integer count;
    @Transient
    private Integer unknown;

    public MatchedLog(Integer id, Boolean liked, String feedback, Integer msglgid) {
        this.id = id;
        this.liked = liked;
        this.likeTime = new Date();
        this.feedback = feedback;
        this.msglgid = msglgid;
    }

    public MatchedLog(Integer count, String matchedName) {
        this.matchedName = matchedName;
        this.count = count;
    }

    public MatchedLog(String xaxis, Integer unknown) {
        this.xaxis = xaxis;
        this.count = 0;
        this.unknown = unknown;
    }

    public MatchedLog(Integer companyId, String companyName, String userId, String userName, String input, MatchedType matchedType, String matchedId, String matchedName) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.userId = userId;
        this.userName = userName;
        this.createTime = new Date();
        this.input = input;
        this.matchedType = matchedType.name();
        this.matchedId = matchedId;
        this.matchedName = matchedName;
        this.liked = Boolean.TRUE;
        this.likeTime = this.createTime;
    }

    public enum MatchedType {
        CASE, FLOW, FAQ, SEARCH
    }
}
