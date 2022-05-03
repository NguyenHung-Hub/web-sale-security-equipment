package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.model.ProductDiscount;
import com.metan.websalesecurityequipment.model.ProductReview;
import com.metan.websalesecurityequipment.service.ProductReviewService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "product")
public class DetailController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductReviewService reviewService;

    private String productId;
    private Integer page;
    private Integer size;
    private String sort;

    @GetMapping(value = "/detail")
    public String getRequest(Model model, @RequestParam(name = "productId") String productId,
                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                             @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                             @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        this.productId = productId;
        this.page = page;
        this.size = size;
        this.sort = sort;
        Product product = productService.findProductById(productId);
        String s = product.getSlug();
        return "redirect:/product/detail/" + s;
    }

    @GetMapping(value = "/detail/{slug}")
    public String showDetail(Model model, @PathVariable(name = "slug", required = false) String slug) {
        Product product = null;
        if (productId == null) {
            product = productService.findBySlug(slug);
        } else {
            product = productService.findProductById(productId);
        }
        List<ProductReview> productReviews = reviewService.findByProductId(productId);
        if (product == null) {
            System.out.println("không tìm thấy");
        }

        //phần đầu
        double discount = product.getPrice() - product.getPrice() * (product.getProductDiscount() == null ? 0 : product.getProductDiscount().getDiscountPercent());
        if (product.getProductDiscount() == null) {
            ProductDiscount p = new ProductDiscount();
            p.setDiscountPercent(0F);
            product.setProductDiscount(p);
        }
        product.setProductReviews(productReviews);
        double rating = 0;
        for (ProductReview pr : productReviews) {
            rating += pr.getRating();
        }
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("review_id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        //review
        model.addAttribute("reviewProduct", new ProductReview());
        model.addAttribute("reviews", reviewService.findByProductId(productId, pageable));
        model.addAttribute("rating", (double) rating / ((productReviews.size() == 0) ? 1 : productReviews.size()));
        model.addAttribute("discount", discount);
        model.addAttribute("product", product);
        return "product-detail";
    }

    public void comment() {

    }
}
