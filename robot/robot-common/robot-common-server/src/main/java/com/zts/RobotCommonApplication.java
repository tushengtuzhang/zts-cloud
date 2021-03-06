package com.zts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

/**
 * @author zhangtusheng
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RobotCommonApplication {
    public static void main(String[] args){

        SpringApplication.run(RobotCommonApplication.class,args);

    }

    /**
     * 不写会抛出懒加载异常，不知道为什么
     * @return
     */
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }
}
