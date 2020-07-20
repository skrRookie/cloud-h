package com.id01.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 @author 01
 @date 2020/7/9 0009 - 20:23
 */
@RestController
public class OrderZkController {

    public static final String INVOKE_URL = "http://cloud-payment-service";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/paymentInfo")
    public String paymentInfo(){
        return restTemplate.getForObject(INVOKE_URL + "/payment/zk", String.class);
    }
}
