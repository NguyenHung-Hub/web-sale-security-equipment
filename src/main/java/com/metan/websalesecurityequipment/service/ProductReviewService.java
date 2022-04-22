package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.ProductReview;

import java.util.List;

public interface ProductReviewService {
    public List<ProductReview> findByProductId(String productId);
}
