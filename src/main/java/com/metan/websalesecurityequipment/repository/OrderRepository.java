package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, String> {

    //lấy số lượng đã bán
    @Query(value = "SELECT sum(oi.quantity) FROM orders o join order_items oi on oi.order_id = o.order_id\n" +
            "where oi.product_id  = ?1 and o.order_status = 'COMPLETED'", nativeQuery = true)
    public Integer getSumQuantity(String productId);
}
