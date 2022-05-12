package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.service.BrandService;
import com.metan.websalesecurityequipment.service.CategoryService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/add-product")
public class AddProductController {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @Autowired
    public AddProductController(ProductService productService, CategoryService categoryService, BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public String saveProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        List<Category> categories2 = new ArrayList<>();


        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);

        System.out.println("sub");
        for (Category c : categories) {
            for (Category c2 : c.getCategories()) {
                System.out.println("out:   " + c2.getName());
                categories2.add(c2);

            }
        }


        return "add_product";
    }

    @PostMapping("/modal")
    public String saveProduct(Product p) {
        System.out.println("product \n\n");
        System.out.println(p.getProductId());
        System.out.println(p.getLongDesc());
        System.out.println(p.getName());
        System.out.println("created At: " + new Date());
        System.out.println("modify At: " + new Date());
        System.out.println("slug: " + toSlug(p.getName()));

        List<Category> categories = categoryService.findAll();
        Category category = categories.get(0);
//        Set<ProductDiscount> discounts = new HashSet<>();

        List<Brand> brands = brandService.findAll();
        Brand brand = brands.get(0);
//        Set<OrderItem> orderItems = new HashSet<>();

//        Set<ProductAttribute> attributes = new HashSet<>();
//        List<ProductReview> reviews = new ArrayList<>();
//        Set<ProductBackdrop> backdrops = new HashSet<>();
        float disPer = 0;


        Product product = new Product(p.getProductId(), p.getName(), p.getQuantity(), p.getPrice(), p.getShortDesc(),
                p.getThumbnail(), p.getName(), category, null, disPer, brand, null, toSlug(p.getName()), p.getLongDesc(),
                new Date(), null, null, null, null);

        productService.saveProduct(product);
        return "redirect:/add-product";
    }

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
