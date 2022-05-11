package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.CartItem;
import com.metan.websalesecurityequipment.model.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {

    @Modifying
    @Transactional
    @Query(value = "delete from cart_items where cart_id =?2 and product_id =?1", nativeQuery = true)
    void deleteById(String product,long cart);
}
