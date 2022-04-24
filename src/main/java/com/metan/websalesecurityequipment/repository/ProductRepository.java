package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findAllByOrderByNameAsc();
    @Query(value = "select p.*, sum(oi.quantity) as tong from products p inner join order_items oi on p.product_id = oi.product_id " +
            "group by p.product_id, p.quantity, p.created_at, " +
            " p.modified_at, p.brand_id, p.category_id, p.discount_id, " +
            "p.long_desc, p.name, p.price, p.short_desc, p.slug, " +
            "p.thumbnail, p.title, p.type_id " +
            "order by tong desc limit 4", nativeQuery = true)
    public List<Product> findTopProduct();
}
