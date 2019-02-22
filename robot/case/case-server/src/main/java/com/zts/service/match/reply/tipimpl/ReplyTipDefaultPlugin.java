package com.zts.service.match.reply.tipimpl;

import com.zts.service.match.reply.ReplyTipHandlerPlugin;
import com.zts.vo.CaseMatchVO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 默认的场景好评差评处理器
 */
@Component
@Order(value = 0)
public class ReplyTipDefaultPlugin implements ReplyTipHandlerPlugin {

    /**
     * 获取tips,
     *
     * @param caseMatchVO 场景匹配vo,已经成功匹配到reply
     * @param originTips  默认预先处理过的tips列表
     * @return
     */
    @Override
    public List<String> getTips(CaseMatchVO caseMatchVO, List<String> originTips) {
        List<String> tips = Optional.ofNullable(originTips).orElse(new ArrayList<>());
        tips.add("好评");
        tips.add("差评");
        return tips;
    }

}
