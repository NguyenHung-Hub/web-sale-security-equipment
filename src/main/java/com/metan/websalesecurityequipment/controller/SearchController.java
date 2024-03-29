package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.model.request.ProductRequestPageable;
import com.metan.websalesecurityequipment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public String showSearch1(ModelMap model,  @RequestParam(name = "keyword",defaultValue = "",required = false) String name,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {

        this.name = name;
        List<Brand> brands = brandService.findAll();
        List<Category> categories = categoryService.findAllParentCategory();
        display(model, name, page, size, brands, categories);
        model.addAttribute("CATEGORIES", categories);

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

        model.addAttribute("brandsFirst", brandsFirst);
        model.addAttribute("brandsLast", brandsLast);
        model.addAttribute("categoriesFirst", categoriesFirst);
        model.addAttribute("categoriesLast", categoriesLast);
        model.addAttribute("productPage", resultPage);
        model.addAttribute("listQuan", getSumQuan(productService.findAll()));
    }

    public HashMap<String, Integer> getSumQuan(List<Product> products) {
        HashMap<String, Integer> listQuantity = new HashMap<>();
        for (Product p : products) {
            listQuantity.put(p.getProductId(),(orderService.getSumQuantity(p.getProductId())==null)?0:orderService.getSumQuantity(p.getProductId()));
        }
        return listQuantity;
    }

}
