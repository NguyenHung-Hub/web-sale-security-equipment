package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM `web-sale-security-equipment`.categories where subcategory_id is null", nativeQuery = true)
    public List<Category> findAll();
}
