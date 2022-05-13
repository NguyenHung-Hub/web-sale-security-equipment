package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Brand;
import com.metan.websalesecurityequipment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "select * from brands order by brand_id desc limit 1", nativeQuery = true)
    public Brand getLastId();
}
