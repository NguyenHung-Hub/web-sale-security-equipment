package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Order;
import com.metan.websalesecurityequipment.repository.OrderRepository;
import com.metan.websalesecurityequipment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}
