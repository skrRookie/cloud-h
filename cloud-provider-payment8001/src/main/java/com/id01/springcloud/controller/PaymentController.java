package com.id01.springcloud.controller;

import com.id01.springcloud.entities.CommonResult;
import com.id01.springcloud.entities.Payment;
import com.id01.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 @author 01
 @date 2020/7/8 0008 - 15:09
 */
@RestController
@Slf4j
public class PaymentController {
    /**
     * 演示服务发现,配合@EnableDiscoveryClient获取微服务相关信息
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 演示服务发现,配合@EnableDiscoveryClient获取微服务相关信息
     */
    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        log.info("services   =====>   "+services.toString());
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                log.info("detail service info  =====>    "+instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
            }
        }
        return this.discoveryClient;
    }

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
