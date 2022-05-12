package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, String> {

    @Override
    <S extends Order> S save(S entity);
    //lấy số lượng đã bán
    @Query(value = "SELECT sum(oi.quantity) FROM orders o join order_items oi on oi.order_id = o.order_id\n" +
            "where oi.product_id  = ?1 and o.order_status = 'completed'", nativeQuery = true)
    public int getSumQuantity(String productId);

    public List<Order> findAll();

}
