package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.CartItem;
import com.metan.websalesecurityequipment.model.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {
    @Override
    void deleteById(CartItemPK cartItemPK);
}
