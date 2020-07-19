package com.id01.springcloud.service.impl;

import com.id01.springcloud.dao.PaymentMapper;
import com.id01.springcloud.entities.CommonResult;
import com.id01.springcloud.entities.Payment;
import com.id01.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 @author 01
 @date 2020/7/8 0008 - 15:03
 */
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    @Value("${server.port}")
    private String serverPort;

    @Override
    public CommonResult create(Payment payment){
        log.info("端口："+serverPort+"的服务  ====》 插入订单信息 ："+payment);
        int result = paymentMapper.create(payment);
        if(result>0){
            log.info("====》 新建订单信息成功！");
            return new CommonResult(200,"服务: "+serverPort+" , 新建订单信息成功！");
        }else{
            log.info("====》 新建订单信息失败！");
            return new CommonResult(500,"服务："+serverPort+" , 新建订单信息失败！");
        }
    }

    @Override
    public CommonResult getPaymentById(@Param("id")Long id){
        log.info("端口："+serverPort+"的服务  ====》 查询订单信息 ："+id);
        return new CommonResult(200,"服务端口："+serverPort,paymentMapper.getPaymentById(id));
    }

}
