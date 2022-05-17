package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.ProductBackdrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductBackdropRepository extends JpaRepository<ProductBackdrop, Long> {
    @Modifying
    @Query(value = "delete from product_backdrops where product_id = :product_id", nativeQuery = true)
    public void deleteAllByProductId(@Param("product_id") String id);
}
