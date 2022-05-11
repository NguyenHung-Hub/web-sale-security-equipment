package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Order;
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
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(String order) {
        orderRepository.deleteById(order);
    }

    @Override
    public Integer getSumQuantity(String productId) {
        if (orderRepository.getSumQuantity(productId) == null) {
            return 0;
        }
        return orderRepository.getSumQuantity(productId);
    }
    @Override
    public String getLastId() {
        String id="";
        try{
            String formatId = orderRepository.getLastId().getOrderId();
            int so =Integer.parseInt(formatId.split("MT-M")[1]) + 1;
            id = "MT-M"+String.valueOf(so);
        }catch (Exception e){
            id = "MT-M1";
        }

        return id;
    }
}
