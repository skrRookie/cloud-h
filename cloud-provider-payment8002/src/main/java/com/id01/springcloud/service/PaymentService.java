package com.id01.springcloud.service;

import com.id01.springcloud.entities.CommonResult;
import com.id01.springcloud.entities.Payment;
import org.springframework.transaction.annotation.Transactional;

/**
 @author 01
 @date 2020/7/8 0008 - 15:02
 */
@Transactional(rollbackFor = Exception.class)
public interface PaymentService {
    /**
     * 新增payment
     * @param payment
     * @return
     */
    CommonResult create(Payment payment);

    /**
     * 查询payment
     * @param id
     * @return
     */
    CommonResult getPaymentById(Long id);
}
