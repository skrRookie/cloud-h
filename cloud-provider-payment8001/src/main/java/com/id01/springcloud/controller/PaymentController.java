package com.id01.springcloud.controller;

import com.id01.springcloud.entities.CommonResult;
import com.id01.springcloud.entities.Payment;
import com.id01.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 @author 01
 @date 2020/7/8 0008 - 15:09
 */
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment")
    public CommonResult create(@RequestBody Payment payment){
        return paymentService.create(payment);
    }

    @GetMapping("/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id){
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/payment/feign/timeOut")
    public String paymentFeignTimeOut(){
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch(Exception e){
            e.printStackTrace();
        }
        return serverPort;
    }

    @PostMapping("/payment/serverPort")
    public String getServerPort(){
        return serverPort;
    }
}
