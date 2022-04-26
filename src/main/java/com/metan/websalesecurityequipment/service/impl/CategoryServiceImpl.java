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
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
