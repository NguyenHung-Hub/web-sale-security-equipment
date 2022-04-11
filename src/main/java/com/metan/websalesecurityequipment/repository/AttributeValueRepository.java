package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, String> {
}
