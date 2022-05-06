package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, String> {
    @Override
    <S extends Order> S save(S entity);
}
