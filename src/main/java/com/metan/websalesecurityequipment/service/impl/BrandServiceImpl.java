package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Brand;
import com.metan.websalesecurityequipment.repository.BrandRepository;
import com.metan.websalesecurityequipment.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        System.out.println(brandRepository.findAll());
        return brandRepository.findAll();
    }
}
