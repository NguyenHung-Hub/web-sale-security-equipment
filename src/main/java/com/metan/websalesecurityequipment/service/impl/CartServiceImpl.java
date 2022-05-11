package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.repository.CartItemRepository;
import com.metan.websalesecurityequipment.repository.CartRepository;
import com.metan.websalesecurityequipment.repository.OrderRepository;
import com.metan.websalesecurityequipment.repository.ProductRepository;

import com.metan.websalesecurityequipment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public double updateQuantity(Integer quantity, String productId, Long cartId) {
        cartRepository.updateQuantity(quantity, productId, cartId);
        Product product = productRepository.findById(productId).get();
        return quantity*product.getPrice();
    }

    public CartItem findByProductAndCart(String productId,Long cartId){
//        CartItem item = cartRepository.findByProductAndCart(productId,cartId);
        return null;
    }

    @Override
    public void deleteCardItem(CartItemPK pk) {
        cartItemRepository.deleteById(pk.getProduct(),pk.getCart());
    }

    @Override
    public Cart findByUser(Long id) {
        return cartRepository.findByUser(id).get();
    }


    @Override
    public void createCartNewUser(User user) {
        Cart cart = new Cart();
        cart.setCreatedAt(new Date());
        cart.setModifiedAt(new Date());
        cart.setUser(user);
        cartRepository.save(cart);
    }

    @Override
    public Cart saveOrUpdateCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
