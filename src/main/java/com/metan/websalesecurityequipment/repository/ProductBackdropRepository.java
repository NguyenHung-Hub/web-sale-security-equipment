package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.ProductBackdrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBackdropRepository extends JpaRepository<ProductBackdrop, Long> {
}
