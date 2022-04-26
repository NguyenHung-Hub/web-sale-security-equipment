package com.metan.websalesecurityequipment.repository;

import com.metan.websalesecurityequipment.model.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query(value = "select * from product_reviews where product_id=?1",nativeQuery = true)
    public List<ProductReview> findByProductId(String productId);

    @Query(value = "select * from product_reviews where product_id=?1",nativeQuery = true)
    public Page<ProductReview> findByProductId(String productId, Pageable pageable);

}
