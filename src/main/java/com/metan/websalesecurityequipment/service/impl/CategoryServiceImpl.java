package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Category;
import com.metan.websalesecurityequipment.repository.CategoryRepository;
import com.metan.websalesecurityequipment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> findAllParentCategory() {
        return categoryRepository.findAllParentCategory();
    }

    @Override
    public List<Category> findSubCategoriesByNameParentCategory(String name) {
        return categoryRepository.findSubCategoriesByNameParentCategory(name);
    }

    @Override
    public Category findCategoryByCategoryId(long id) {
        return categoryRepository.findCategoryByCategoryId(id);
    }

}
