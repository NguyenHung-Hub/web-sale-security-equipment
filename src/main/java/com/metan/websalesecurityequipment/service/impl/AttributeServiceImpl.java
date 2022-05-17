package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Attribute;
import com.metan.websalesecurityequipment.repository.AttributeRepository;
import com.metan.websalesecurityequipment.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {
    @Autowired
    private AttributeRepository attributeRepository;


    @Override
    public List<Attribute> findAllOrderByName() {
        return attributeRepository.findAllOrderByName();
    }

    @Override
    public List<Attribute> findAttributesByCategoryCategoryId(long id) {
        return attributeRepository.findAttributesByCategoryCategoryId(id);
    }
}
