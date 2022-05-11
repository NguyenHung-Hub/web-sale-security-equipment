package com.metan.websalesecurityequipment.service;


import com.metan.websalesecurityequipment.model.Order;
import org.springframework.stereotype.Service;


public interface OrderService {
    void save(Order order);
    public int getSumQuantity(String productId);

    String getLastId();
}

