package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<Product, String> {
}
