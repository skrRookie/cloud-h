package com.id01.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 @author 01
 @date 2020/7/19 0019 - 15:05
 */
@SpringBootApplication
@MapperScan(basePackages = "com.id01.springcloud.dao")
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class);
    }
}
