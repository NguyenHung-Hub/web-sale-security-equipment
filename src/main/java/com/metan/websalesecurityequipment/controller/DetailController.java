package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.config.security.MyUserDetails;
import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.model.request.ProductReviewRequest;
import com.metan.websalesecurityequipment.service.CartService;
import com.metan.websalesecurityequipment.service.ProductReviewService;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "product")
public class DetailController {
    private final ProductService productService;
    private final ProductReviewService reviewService;
    private final CartService cartService;
    private String productId;

    @Autowired
    public DetailController(ProductService productService, ProductReviewService reviewService, CartService cartService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.cartService = cartService;
    }

    @GetMapping(value = "/detail/{slug}")
    public String getRequest(Model model, @PathVariable(name = "slug", required = true) String slug,
                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                             @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                             @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        System.out.println(slug);
        Product product = productService.findBySlug(slug);
        productId = product.getProductId();
        List<ProductReview> productReviews = reviewService.findByProductId(productId);
        List<Product> top4ProductsRand = productService.findTopNumberRandom(4);


        //phần đầu
//        double discount = product.getPrice() - product.getPrice() * (product.getProductDiscount() == null ? 0 : product.getProductDiscount().getDiscountPercent());
//        if (product.getProductDiscount() == null) {
//            ProductDiscount p = new ProductDiscount();
//            p.setDiscountPercent(0F);
//            product.setProductDiscount(p);
//        }
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
            sortable = Sort.by("review_id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);


        ProductReview productReview = new ProductReview();
        productReview.setProduct(product);

        //review
        model.addAttribute("reviewProduct", productReview);
        model.addAttribute("reviews", reviewService.findByProductId(productId, pageable));
        model.addAttribute("rating", (double) rating / ((productReviews.size() == 0) ? 1 : productReviews.size()));
        model.addAttribute("product", product);
        model.addAttribute("rand4Product", top4ProductsRand);
        return "product-detail";

    }

    @PostMapping(value = "/reviews")
    public String addReview(@ModelAttribute("productReview") ProductReview productReview) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        if (productReview.getContent() == null) {
            productReview.setContent("");
        }
        Product product = productService.findProductById(productId);
        if (productService.checkBuyCompletedProductByUser(product.getProductId(), userDetails.getUser().getUserId())) {
            productReview.setProduct(product);
            productReview.setUser(userDetails.getUser());
            ProductReview s = reviewService.save(productReview);
            return "redirect:/product/detail/" + product.getSlug() + "?" + !(s == null);
        }
        return "redirect:/product/detail/" + product.getSlug() + "?nopay";


    }

    @PostMapping(value = "/api/productReviews")
    @ResponseBody
    public Page<ProductReview> getReviews(@RequestBody ProductReviewRequest req) {
        Sort sortable = null;
        sortable = Sort.by("review_id").descending();
        Pageable pageable = PageRequest.of(req.getPage(), 5, sortable);
        return reviewService.findByProductId(productId, pageable);
    }

    @PostMapping(value = "/addToCart")
    public String addToCart(@RequestParam("quantity") int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Product product = productService.findProductById(productId);

        Cart cart = user.getCart();
        if (cart == null) {
            cartService.createCartNewUser(user);
            cart = cartService.findByUser(user.getUserId());
        }
//        List<CartItem> cartItems = cart.getCartItems();
        List<CartItem> cartItems2 = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCreatedAt(new Date());
        cartItem.setModifiedAt(new Date());
        cart.setTotal(10000D);
        cartItems2.add(cartItem);
        cart.setCartItems(cartItems2);

        cart = cartService.saveOrUpdateCart(cart);
        return "redirect:/cart";
    }
}
