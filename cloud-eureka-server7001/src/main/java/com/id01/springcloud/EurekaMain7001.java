package com.id01.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 @author 01
 @date 2020/7/8 0008 - 22:22
 */
@SpringBootApplication
//EnableEurekaServer  表示这个服务就是服务注册中心
@EnableEurekaServer
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class);
    }
}
