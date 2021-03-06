package com.metan.websalesecurityequipment.service;


import antlr.collections.impl.LList;
import com.metan.websalesecurityequipment.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {
    public Integer getSumQuantity(String productId);

    void save(Order order);
    public List<Order> findAll();

    void deleteOrder(String order);
    String getLastId();

    void completeOrder(String orderId);
    public Page<Order> findOrderByStatus(String status, Pageable pageable);
    public Order findById(String id);
}

