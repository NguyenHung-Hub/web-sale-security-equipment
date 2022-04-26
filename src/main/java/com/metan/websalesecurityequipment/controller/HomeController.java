package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Category;
import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.service.CategoryService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @Autowired
    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showFormHome(Model model) {
        List<Product> products = productService.findAll();
        List<Product> topProducts = productService.findTopProduct();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("PRODUCT_LIST", products);
        model.addAttribute("TOP_PRODUCT_LIST", topProducts);
        model.addAttribute("CATEGORIES", categories);

        return "home-demo";
    }

}
