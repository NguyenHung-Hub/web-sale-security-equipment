package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.model.request.ProductFileRequest;
import com.metan.websalesecurityequipment.service.AwsService;
import com.metan.websalesecurityequipment.service.BrandService;
import com.metan.websalesecurityequipment.service.CategoryService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/add-product")
public class AddProductController {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private final AwsService awsService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @Autowired
    public AddProductController(ProductService productService, CategoryService categoryService, BrandService brandService, AwsService awsService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.awsService = awsService;
    }

    @GetMapping
    public String saveProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);

        System.out.println("sub");
        for (Category c :
                categories) {
            for (Category c2 :
                    c.getCategories()) {
                System.out.println("out:   " + c2.getName());

            }

        }


        return "add_product";
    }

    @PostMapping(value = "/modal")
    public String saveProduct(@ModelAttribute("product") Product p,
                              @RequestParam("image") MultipartFile img) {
        String fileName = awsService.save(img);
        p.setThumbnail("https://chinh1506.s3.amazonaws.com/" + fileName);
        p.setTitle(p.getName() + " " + p.getProductId());
        p.setCreatedAt(new Date());
        p.setModifiedAt(new Date());
        p.setSlug(toSlug(p.getTitle()));
        productService.saveProduct(p);
        System.out.println("Da them");
        return "redirect:/add-product";
    }
    @PostMapping(value = "/modal/update")
    public String updateProduct(@ModelAttribute("product") Product p,
                              @RequestParam("image") MultipartFile img) {
        String fileName = awsService.save(img);
        p.setThumbnail("https://chinh1506.s3.amazonaws.com/" + fileName);
        p.setTitle(p.getName() + " " + p.getProductId());
        p.setCreatedAt(new Date());
        p.setModifiedAt(new Date());
        p.setSlug(toSlug(p.getTitle()));
        productService.saveProduct(p);
        System.out.println("Da them");
        return "redirect:/add-product";
    }
    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
