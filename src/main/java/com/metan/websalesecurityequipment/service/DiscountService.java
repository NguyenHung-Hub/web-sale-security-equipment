package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Discount;

import java.util.Optional;

public interface DiscountService {
    public void saveDiscountEvent(Discount discount);
    public Optional<Discount> findDiscountByName(String name);
}
