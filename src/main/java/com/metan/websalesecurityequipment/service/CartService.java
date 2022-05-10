package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.*;
import org.springframework.stereotype.Service;


public interface CartService {
    Cart findById(Long id);
    double updateQuantity(Integer quantity,String productId,Long cartId);
    CartItem findByProductAndCart(String productId, Long cartId);

    void deleteCardItem(CartItemPK pk);

    Cart findByUser(Long id);
    public void createCartNewUser(User user);
}
