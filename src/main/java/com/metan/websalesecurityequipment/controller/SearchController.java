package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.model.request.ProductRequestPageable;
import com.metan.websalesecurityequipment.repository.OrderRepository;
import com.metan.websalesecurityequipment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping
public class SearchController {
    private Pageable pageable = null;
    private String name;
    private Page<Product> resultPage = null;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private OrderService orderService;

    public SearchController() {
    }


    @GetMapping(value = "search")
    public String showSearch1(ModelMap model, @RequestParam(name = "name", required = false) String name,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {
        this.name = name;
        List<Brand> brands = brandService.findAll();
        List<Category> categories = categoryService.findAll();
        display(model, name, page, size, brands, categories);

        return "search";
    }

    @PostMapping("search/products")
    @ResponseBody
    public Page<Product> getProductsFilter(ModelMap model,
                                           @RequestBody ProductRequestPageable req,
                                           @RequestParam("page") Optional<Integer> page,
                                           @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(req.getPage());
        int pageSize = size.orElse(12);
        Sort sort;
        System.out.println(req);
        String ss = "DESC";
        if (req.getColumnName().equals("ascPrice")) {
            ss = "ASC";
            req.setColumnName("price");
        } else if (req.getColumnName().equals("name")) {
            ss = "ASC";
        } else if (req.getColumnName().equals("descPrice")) {
            ss = "DESC";
            req.setColumnName("price");
        }

        if (ss.equals("DESC")) {
            sort = Sort.by(req.getColumnName()).descending();
        } else {
            sort = Sort.by(req.getColumnName()).ascending();
        }
        pageable = PageRequest.of(currentPage, pageSize, sort);
        Page<Product> productPage = productService.searchByNameCateBrand(req, name, pageable);
        return productPage;
    }

    public Page<Product> getProductByName(String name, Pageable pageable) {
        //tìm kiếm theo tên product
        if (StringUtils.hasText(name)) {
            resultPage = productService.findByNameContaining(name, pageable);
        } else {
            resultPage = productService.findAll(pageable);
        }
        return resultPage;
    }

    public HashMap<String, Double> getAvgRating(List<Product> products) {
        HashMap<String, Double> listRating = new HashMap<>();
        List<ProductReview> reviews = new ArrayList<>();
        for (Product p : products) {
            Double avgRating = 0.0;
            reviews = productReviewService.findByProductId(p.getProductId());
            for (ProductReview pr : reviews) {
                avgRating += pr.getRating();
            }
            listRating.put(p.getProductId(), avgRating / (reviews.size() == 0 ? 1 : reviews.size()));
        }
        return listRating;
    }

    public void display(ModelMap model, String name,
                        Optional<Integer> page, Optional<Integer> size,
                        List<Brand> brands, List<Category> categories) {
        List<Brand> brandsFirst = null;
        List<Brand> brandsLast = null;

        List<Category> categoriesFirst = null;
        List<Category> categoriesLast = null;
        if (brands.size() <= 5) {
            model.addAttribute("brandsFirst", brands);
        } else {
            brandsFirst = brands.subList(0, 5);
            brandsLast = brands.subList(5, brands.size());
        }
        if (categories.size() <= 5) {
            model.addAttribute("categoriesFirst", categories);
        } else {
            categoriesFirst = categories.subList(0, 5);
            categoriesLast = categories.subList(5, categories.size());
        }

        //Nếu nguoi dung khong nhap
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(12);

        pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
        resultPage = getProductByName(name, pageable);
        //System.out.println("my detail"+resultPage.getContent().get(0).getOrderItems());
        model.addAttribute("brandsFirst", brandsFirst);
        model.addAttribute("brandsLast", brandsLast);
        model.addAttribute("categoriesFirst", categoriesFirst);
        model.addAttribute("categoriesLast", categoriesLast);
        model.addAttribute("productPage", resultPage);
        model.addAttribute("listQuan", getSumQuan(productService.findAll()));
        model.addAttribute("listRating", getAvgRating(productService.findAll()));
    }

    public HashMap<String, Integer> getSumQuan(List<Product> products) {
        HashMap<String, Integer> listQuantity = new HashMap<>();
        for (Product p : products) {
            listQuantity.put(p.getProductId(),(orderService.getSumQuantity(p.getProductId())==null)?0:orderService.getSumQuantity(p.getProductId()));
        }
        return listQuantity;
    }

}
