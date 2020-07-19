package com.id01.springcloud.controller;

import com.id01.springcloud.entities.CommonResult;
import com.id01.springcloud.entities.Payment;
import com.id01.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 参数列表中使用@RequestBody注解为了解决consumer调用新增时，数据库中为null的问题
     * @param payment
     * @return
     */
    @PostMapping("/payment")
    public CommonResult create(@RequestBody Payment payment){
        return paymentService.create(payment);
    }

    @GetMapping("/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id){
        return paymentService.getPaymentById(id);
    }


    @PostMapping("/payment/serverPort")
    public String getServerPort(){
        return serverPort;
    }
}
