package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductReviewService {
    public List<ProductReview> findByProductId(String productId);
    public Page<ProductReview> findByProductId(String productId, Pageable pageable);
}
