package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Brand;
import com.metan.websalesecurityequipment.model.Category;
import com.metan.websalesecurityequipment.model.Order;
import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private final OrderService orderService;
    private final AwsService awsService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public Dashboard(OrderService orderService, AwsService awsService, ProductService productService,
                     CategoryService categoryService, BrandService brandService) {
        this.orderService = orderService;
        this.awsService = awsService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public String dashboard(Model model) {

        return "redirect:/dashboard/product";
    }
    @GetMapping("/product")
    public String product(Model model) {
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
        for (Category c :
                categories) {
            for (Category c2 :
                    c.getCategories()) {
                System.out.println("out:   " + c2.getName());

            }
        }

        return "product_db";
    }

    @GetMapping("/brand")
    public String brand(Model model) {

        return "brand_db";
    }

    @GetMapping("/order")
    public String order(Model model) {

        List<Order> orders = orderService.findAll();
//        System.out.println(orders.get(0).getContent());
        model.addAttribute("orders", orders);
        return "order_db";
    }



    @PostMapping(value = "/product/modal")
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
        return "redirect:/dashboard/product";
    }
    @PostMapping(value = "/product/update")
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
        return "redirect:/dashboard/product";
    }
    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }


}
