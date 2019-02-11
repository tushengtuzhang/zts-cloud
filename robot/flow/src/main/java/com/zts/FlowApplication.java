package com.zts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhangtusheng
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FlowApplication {

    public static void main(String[] args){
        SpringApplication.run(FlowApplication.class,args);
    }
}
