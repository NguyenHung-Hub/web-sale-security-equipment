package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Order;
import com.metan.websalesecurityequipment.repository.OrderRepository;
import com.metan.websalesecurityequipment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);}

    @Override
    public int getSumQuantity(String productId) {
        return orderRepository.getSumQuantity(productId);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
