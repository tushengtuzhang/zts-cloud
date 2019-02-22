package com.zts;

import com.zts.listener.SimilarityListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangtusheng
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
public class SimilarityApplication {

    public static void main(String[] args){
        SpringApplication springApplication = new SpringApplication(SimilarityApplication .class);
        springApplication.addListeners(new SimilarityListener());
        springApplication.run(args);

    }
}
