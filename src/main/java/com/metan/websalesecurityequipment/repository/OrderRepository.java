package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Order;
import com.metan.websalesecurityequipment.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "where oi.product_id  = ?1 and o.order_status = 'COMPLETED'", nativeQuery = true)
    public Integer getSumQuantity(String productId);

    @Query(value = "select * from orders order by order_id desc limit 1", nativeQuery = true)
    public Order getLastId();

    @Modifying
    @Query(value = "update orders set order_status = \"CANCELLED\" where order_id =?1", nativeQuery = true)
    public void deleteById(String id);

    public List<Order> findAll();
    @Query(value = "select * from orders where order_status = ?1", nativeQuery = true)
    public  Page<Order> findOrderByStatus(String status, Pageable pageable);
}
