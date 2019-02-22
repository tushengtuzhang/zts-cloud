package com.zts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangtusheng
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory, @Autowired(required = false) @Qualifier("mappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        RestTemplate restTemplate = new RestTemplate(factory);
        //注入 自定义的json处理器
        if (mappingJackson2HttpMessageConverter != null) {
            //替换默认的处理器
            restTemplate.getMessageConverters().removeIf(httpMessageConverter -> {
                return httpMessageConverter instanceof MappingJackson2HttpMessageConverter;
            });
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        }
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(15000);
        factory.setConnectTimeout(15000);
        return factory;
    }
}

