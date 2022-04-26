package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
