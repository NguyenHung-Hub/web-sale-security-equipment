package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Discount;
import com.metan.websalesecurityequipment.repository.DiscountRepository;
import com.metan.websalesecurityequipment.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public void saveDiscountEvent(Discount discount) {
        discountRepository.saveDiscountEvent(discount.getName(), discount.getCreatedAt(), discount.getModifiedAt(), discount.getEndDate());
    }

    @Override
    public Optional<Discount> findDiscountByName(String name) {
        return discountRepository.findById(name);
    }
}
