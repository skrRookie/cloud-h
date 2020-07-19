package com.id01.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 @author 01
 @date 2020/7/8 0008 - 17:44
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * RestTemplate提供http调用
     * REST api 非RPC
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
