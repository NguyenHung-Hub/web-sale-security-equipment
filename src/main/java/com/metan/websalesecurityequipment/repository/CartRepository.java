package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Cart;
import com.metan.websalesecurityequipment.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Override
    Optional<Cart> findById(Long aLong);

    @Query(value = "UPDATE CartItem ci SET ci.quantity =?1 WHERE ci.product.productId=?2 AND ci.cart.cartId=?3")
    @Modifying(clearAutomatically = true)
    void updateQuantity(Integer quantity,String productId,Long cartId);

//    CartItem findByProductAndCart(String productId,Long cartId);
}
