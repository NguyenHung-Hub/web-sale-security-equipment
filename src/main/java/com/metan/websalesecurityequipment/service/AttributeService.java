package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Attribute;

import java.util.List;

public interface AttributeService {
    public List<Attribute> findAllOrderByName();

    public List<Attribute> findAttributesByCategoryCategoryId(long id);
}
