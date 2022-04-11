package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    private final TestRepository testRepository;

    @Autowired
    public ProductRestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping
    public @ResponseBody
    Iterable<Product> findAllProducts() {
        return testRepository.findAll();
    }
}
