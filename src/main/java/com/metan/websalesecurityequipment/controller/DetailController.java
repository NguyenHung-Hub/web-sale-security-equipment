package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.model.ProductReview;
import com.metan.websalesecurityequipment.service.ProductReviewService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String showDetail(Model model, @RequestParam(name = "productId", required = false) String productId) {
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
        //review
        model.addAttribute("reviews",productReviews);
        model.addAttribute("rating",(double)rating/((productReviews.size()==0)?1:productReviews.size()));
        model.addAttribute("discount",discount);
        model.addAttribute("product",product);
        return "product-detail";
    }
    public void comment(){

    }
}
