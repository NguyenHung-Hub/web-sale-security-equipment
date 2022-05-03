package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findAllByOrderByNameAsc();

    @Query(value = "select p.*, sum(oi.quantity) as tong from products p inner join order_items oi on p.product_id = oi.product_id " +
            "group by p.product_id, p.quantity, p.created_at, " +
            " p.modified_at, p.brand_id, p.category_id, p.discount_id, " +
            "p.long_desc, p.name, p.price, p.short_desc, p.slug, " +
            "p.thumbnail, p.title, p.type_id " +
            "order by tong desc limit 4", nativeQuery = true)
    public List<Product> findTopProduct();

    //Hao
    public List<Product> findByNameContaining(String name);

    @Query(value = "select * from products p join brands b on p.brand_id = b.brand_id join categories c on c.category_id = p.category_id " +
            "where concat(c.name, b.name) REGEXP ?1", nativeQuery = true)
    public Page<Product> searchByNameCateBrand(String name,Pageable pageable);

    public Page<Product> findAll(Pageable pageable);

    public Page<Product> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select * from products p join brands b on p.brand_id= b.brand_id join categories c on c.category_id = p.category_id " +
            "where b.brand_id in ?2 " +
            "OR c.category_id in ?1", nativeQuery = true)
    public Page<Product> searchByNameCateBrand(List<Integer> cates,List<Integer> brands,Pageable pageable);

}
