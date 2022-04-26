package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TestRepository extends CrudRepository<Product, String> {
}
