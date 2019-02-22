package com.zts.service.match.reply;

import com.zts.vo.CaseMatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 回复处理器
 * <p>
 * 默认注册有两个 处理器
 * {@link com.zts.service.match.reply.tipimpl.ReplyTipDefaultPlugin}
 * {@link com.zts.service.match.reply.tipimpl.ReplyTipRelationQuestionPlugin}
 *
 * @author zhangtusheng
 * @date 2018/12/18
 */
@Service
public class ReplyTipHandlerService {
    @Autowired
    List<ReplyTipHandlerPlugin> replyTipHandlerPlugins;

    public List<String> getTips(final CaseMatchVO caseMatchVO) {
        List<String> tips = new ArrayList<>();

        List<ReplyTipHandlerPlugin> plugins = Optional.ofNullable(replyTipHandlerPlugins).orElse(new ArrayList<>());

        for (ReplyTipHandlerPlugin plugin : plugins) {
            tips = plugin.getTips(caseMatchVO, tips);
        }

        return tips;
    }
}
