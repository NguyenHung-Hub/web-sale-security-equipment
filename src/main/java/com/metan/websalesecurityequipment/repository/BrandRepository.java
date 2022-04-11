package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
