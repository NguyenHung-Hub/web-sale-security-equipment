package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query(value = "from Attribute order by name")
    public List<Attribute> findAllOrderByName();

    @Query(value = "select a.* from attributes a left join categories c1 on a.category_id = c1.category_id left join categories c2 on c1.parent_category_id = c2.category_id where c1.category_id = :id or c2.category_id = :id or a.category_id is null", nativeQuery = true)
    public List<Attribute> findAttributesByCategoryCategoryId(@Param("id") long id);
}
