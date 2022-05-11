package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.ProductReview;
import com.metan.websalesecurityequipment.repository.ProductReviewRepository;
import com.metan.websalesecurityequipment.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {
    @Autowired
    private ProductReviewRepository reviewRepository;

    @Override
    @Transactional
    public List<ProductReview> findByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public Page<ProductReview> findByProductId(String productId, Pageable pageable) {return reviewRepository.findByProductId(productId,pageable);
    }

    @Override
    public ProductReview save(ProductReview productReview) {
        return reviewRepository.save(productReview);
    }
}
