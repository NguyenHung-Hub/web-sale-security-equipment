package com.metan.websalesecurityequipment.service;


import com.metan.websalesecurityequipment.model.Order;
import org.springframework.stereotype.Service;


public interface OrderService {
    public Integer getSumQuantity(String productId);

    void save(Order order);
}

