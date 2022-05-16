package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Category;
import com.metan.websalesecurityequipment.model.Discount;
import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.service.CategoryService;
import com.metan.websalesecurityequipment.service.DiscountService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
public class HomeController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final DiscountService discountService;


    @Autowired
    public HomeController(ProductService productService, CategoryService categoryService, DiscountService discountService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.discountService = discountService;
    }

    @GetMapping
    public String showFormHome(Model model) {
        List<Product> products = productService.findAll();
        List<Product> topProducts = productService.findTopProduct();
        List<Product> newProducts = productService.findProductsNew();
        List<Category> categories = categoryService.findAllParentCategory();
        Discount discount = discountService.findDiscountByName("Khuyến mãi hot").get();

        List<Category> phuKienCategories = categoryService.findSubCategoriesByNameParentCategory("Phụ kiện");

        Map<Category, List<Product>> categoryListMap = new LinkedHashMap<>();

        for(Category category: categories) {
            categoryListMap.put(category, productService.findProductsByCategory(category.getCategoryId()));
        }

        model.addAttribute("TOP_PRODUCT_LIST", topProducts);
        model.addAttribute("NEW_PRODUCT_LIST", newProducts);
        model.addAttribute("CATEGORIES", categories);
        model.addAttribute("discount", discount);
        model.addAttribute("categoryListMap", categoryListMap);
        model.addAttribute("PHU_KIEN_CATEGORIES", phuKienCategories);

        return "home";
    }
}
