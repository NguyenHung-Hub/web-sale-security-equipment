package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Cart;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.repository.CartRepository;
import com.metan.websalesecurityequipment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void createCartNewUser(User user) {
        Cart cart = new Cart();
        cart.setCreatedAt(new Date());
        cart.setModifiedAt(new Date());
        cart.setUser(user);
        cartRepository.save(cart);
    }
}
