package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.repository.OrderRepository;
import com.metan.websalesecurityequipment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public int getSumQuantity(String productId) {
        return orderRepository.getSumQuantity(productId);
    }
}
