package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Cart;
import com.metan.websalesecurityequipment.model.CartItem;
import com.metan.websalesecurityequipment.model.CartItemPK;
import com.metan.websalesecurityequipment.model.Order;
import org.springframework.stereotype.Service;


public interface CartService {
    Cart findById(Long id);
    double updateQuantity(Integer quantity,String productId,Long cartId);
    CartItem findByProductAndCart(String productId, Long cartId);

    void deleteCardItem(CartItemPK pk);
}
