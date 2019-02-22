package com.zts.service.match.reply;

import com.zts.vo.CaseMatchVO;

import java.util.List;

/**
 * 场景回复插件
 * <p>
 * 提供插入式，提供额外的tip处理
 *
 * @author zipeng
 * @date 2019/1/22
 */
public interface ReplyTipHandlerPlugin {

    /**
     * 获取tips,
     *
     * @param caseMatchVO 场景匹配vo,已经成功匹配到reply
     * @param originTips  默认预先处理过的tips列表
     * @return 必须不能为空的list
     */
    List<String> getTips(CaseMatchVO caseMatchVO, List<String> originTips);
}
