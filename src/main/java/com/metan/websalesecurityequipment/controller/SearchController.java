package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Brand;
import com.metan.websalesecurityequipment.model.Category;
import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.service.BrandService;
import com.metan.websalesecurityequipment.service.CategoryService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping
public class SearchController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping(value = "search")
    public String showSearch1(ModelMap model, @RequestParam(name = "name") String name,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {

        List<Brand> brands = brandService.findAll();
        List<Category> categories = categoryService.findAll();

        List<Brand> brandsFirst = null;
        List<Brand> brandsLast = null;

        List<Category> categoriesFirst = null;
        List<Category> categoriesLast = null;

        if (brands.size() <= 4) {
            model.addAttribute("brandsFirst", brands);
        } else {
            brandsFirst = brands.subList(0, 4);
            brandsLast = brands.subList(4, brands.size());
        }
        if (categories.size() <= 4) {
            model.addAttribute("categoriesFirst", categories);
        } else {
            categoriesFirst = categories.subList(0, 4);
            categoriesLast = categories.subList(4, categories.size());
        }

        //Nếu nguoi dung khong nhap
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(15);

        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
        Page<Product> resultPage = null;

        if (StringUtils.hasText(name)) {
            resultPage = productService.findByNameContaining(name, pageable);
            model.addAttribute("name", name);
        } else {
            resultPage = productService.findAll(pageable);
        }

        model.addAttribute("brandsFirst", brandsFirst);
        model.addAttribute("brandsLast", brandsLast);
        model.addAttribute("categoriesFirst", categoriesFirst);
        model.addAttribute("categoriesLast", categoriesLast);
        model.addAttribute("productPage", resultPage);
        model.addAttribute("productPage", resultPage);

        int totalPages = resultPage.getTotalPages() - 1;
        if (totalPages > 0) {
            int start = Math.max(0, currentPage - 4);
            int end = Math.min(currentPage + 4, totalPages);

            if (totalPages >= 5) {
                if (end == totalPages) start = end - 5;
                else if (start == 0) end = start + 5;
            } else if (totalPages >= 3) {
                if (end == totalPages) start = end - 3;
                else if (start == 1) end = start + 3;
            } else if (totalPages >= 1) {
                if (end == totalPages) start = end - 1;
                else if (start == 1) end = start + 1;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "searchPaginated";
    }

}
