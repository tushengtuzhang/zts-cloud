package com.zts.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jeanming1
 * @date 2017/8/3
 */
@Data
public class ThirdPartyServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * {@link com.yglink.otalk.thirdPartyService.service.ThirdParty#TRAIN_SERVICE_ID}
     * {@link com.yglink.otalk.thirdPartyService.service.ThirdParty#WEATHER_SERVICE_ID}
     * {@link com.yglink.otalk.thirdPartyService.service.ThirdParty#INVOICE_SERVICE_ID}
     */
    /** 服务id */
    private Integer serviceId;
    /** 公司服务id */
    private Integer companyServiceId;

    private Integer companyId;
    private String userId;
    private Boolean anonymous;
    /** 动作参数 */
    private Map<String, String> param;

    private CaseMatchVO caseMatchVO;

    public ThirdPartyServiceVO() {
        this.param = new HashMap<>();
    }


}
