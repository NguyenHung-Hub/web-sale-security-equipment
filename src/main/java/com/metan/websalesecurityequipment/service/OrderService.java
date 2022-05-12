package com.metan.websalesecurityequipment.service;


import antlr.collections.impl.LList;
import com.metan.websalesecurityequipment.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {
    void save(Order order);
    public int getSumQuantity(String productId);
    public List<Order> findAll();
}

