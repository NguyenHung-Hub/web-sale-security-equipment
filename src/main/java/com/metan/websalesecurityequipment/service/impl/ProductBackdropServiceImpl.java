package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.repository.ProductBackdropRepository;
import com.metan.websalesecurityequipment.service.ProductBackdropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBackdropServiceImpl implements ProductBackdropService {
    @Autowired
    private ProductBackdropRepository productBackdropRepository;

    @Override
    public void deleteAllByProductId(String id) {
        productBackdropRepository.deleteAllByProductId(id);
    }
}
