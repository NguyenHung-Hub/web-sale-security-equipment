package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.config.security.MyUserDetails;
import com.metan.websalesecurityequipment.model.Cart;
import com.metan.websalesecurityequipment.model.CartItemPK;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.repository.UserRepository;
import com.metan.websalesecurityequipment.service.CartService;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PostMapping("/update/{productId}/{quantity}")
    public String update(
            @PathVariable("productId") String productId,
            @PathVariable("quantity") int quantity)
    {
        System.out.println(productId + " " + quantity);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = user.getCart();
//        User user = userRepository.findById(2L).get();
//        Cart cart = user.getCart();
        double subtotal = cartService.updateQuantity(quantity,productId,cart.getCartId());
        return String.valueOf(subtotal);
    };

    @PostMapping("/delete/{productId}")
    public String delete(
            @PathVariable("productId") String productId)
    {
//        User user = userRepository.findById(2L).get();
//        Cart cart = user.getCart();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = user.getCart();
        cartService.deleteCardItem(new CartItemPK(productId, cart.getCartId()));
        return productId;
    };
}
