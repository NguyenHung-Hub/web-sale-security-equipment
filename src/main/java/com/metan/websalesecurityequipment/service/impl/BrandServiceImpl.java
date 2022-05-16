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

    @Override
    public long getLastId() {
        long id = 1;
        try {
//            String formatId = orderRepository.getLastId().getOrderId();
            id = brandRepository.getLastId().getBrandId();
//            int so = Integer.parseInt(formatId.split("MT-M")[1]) + 1;
//            id = "MT-M"+String.valueOf(so);
        } catch (Exception e) {
//            id = "MT-M1";
        }

        return id;
    }
}
