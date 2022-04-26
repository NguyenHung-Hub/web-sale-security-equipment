package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}
