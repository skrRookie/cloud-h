package com.id01.springcloud.dao;

import com.id01.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 @author 01
 @date 2020/7/8 0008 - 14:43
 */
public interface PaymentMapper {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
