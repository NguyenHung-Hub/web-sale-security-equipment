package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.model.ProductReview;
import com.metan.websalesecurityequipment.service.ProductReviewService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping(value = "detail")
    public String showDetail(Model model, @RequestParam(name = "productId", required = false) String productId,
                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                             @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                             @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        Product product= productService.findProductById(productId);
        if(product== null){
            System.out.println("không tìm thấy");
        }
        //phần đầu
        double discount= product.getPrice()- product.getPrice()*product.getProductDiscount().getDiscountPercent();
        List<ProductReview> productReviews= reviewService.findByProductId(productId);
        product.setProductReviews(productReviews);
        double rating=0;
        for (ProductReview pr:productReviews){
            rating+=pr.getRating();
        }
        Sort sortable= null;
        if(sort.equals("ASC")){
            sortable= Sort.by("review_id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable= PageRequest.of(page,size,sortable);
        //review
        model.addAttribute("reviewProduct",new ProductReview());
        model.addAttribute("reviews",reviewService.findByProductId(productId,pageable));
        model.addAttribute("rating",(double)rating/((productReviews.size()==0)?1:productReviews.size()));
        model.addAttribute("discount",discount);
        model.addAttribute("product",product);
        return "product-detail";
    }
    public void comment(){

    }
}
