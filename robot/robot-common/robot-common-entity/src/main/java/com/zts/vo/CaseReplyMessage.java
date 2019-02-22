package com.zts.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangtusheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseReplyMessage implements Message{

    private Integer caseId;
    /**
    生命周期的状态 effective finished
     */
    private String lifeCycle;
    private Integer replyId;
    /**
     * 场景关联的文件对象
     */
    private Ref ref;

    private String question;

    /**
     * 动作替换触发（下划线）
     */
    private List<Action> actions;
    private String reply;

    private List<String> bubbleList;

    private Integer matchedLogId;

    public CaseReplyMessage(Integer caseId, Integer replyId, String lifeCycle, Ref ref, String question, List<Action> actions, String reply, List<String> bubbleList,Integer matchedLogId) {
        this.caseId = caseId;
        this.replyId = replyId;
        this.lifeCycle=lifeCycle;
        this.ref = ref;
        this.question = question;
        this.actions = actions;
        this.reply = reply;
        this.bubbleList = bubbleList;
        this.matchedLogId = matchedLogId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Ref implements Serializable {
        private String refId="";
        private String refName="";
        private String refText="";

    }

}
