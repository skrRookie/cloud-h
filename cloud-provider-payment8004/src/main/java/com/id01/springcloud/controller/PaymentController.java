package com.id01.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 @author 01
 @date 2020/7/8 0008 - 15:09
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/zk")
    public String paymentZK(){
        return "SpringCloud with zookeeper :"+ serverPort + UUID.randomUUID().toString();
    }

}
