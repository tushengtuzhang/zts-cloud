package com.zts.listener;

import com.zts.service.SimilarityService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtusheng
 */
@Service
public class SimilarityListener implements ApplicationListener<ApplicationStartedEvent> {
    @Resource
    private SimilarityService similarityService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        //similarityService.initData();
    }
}
