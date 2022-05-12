package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findAll();
    public List<Category> findSubCategoriesByNameParentCategory(String name);
}
