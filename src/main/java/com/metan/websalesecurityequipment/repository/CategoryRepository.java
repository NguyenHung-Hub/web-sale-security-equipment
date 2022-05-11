package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories where parent_category_id is null", nativeQuery = true)
    public List<Category> findAll();

    @Query(value = "select * from categories c join categories cp on c.parent_category_id = cp.category_id where cp.name = :name", nativeQuery = true)
    public List<Category> findSubCategoriesByNameParentCategory(@Param("name") String name);
}
