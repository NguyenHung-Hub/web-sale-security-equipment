package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Brand;

import java.util.List;

public interface BrandService {
    public List<Brand> findAll();
    public long getLastId();
    public Brand findBrandById(long id);
}
