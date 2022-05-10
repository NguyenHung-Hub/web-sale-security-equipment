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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/updateUser/{firstName}/{lastName}/{phoneNumber}/{profile}")
    public String updateUser(
            @PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName,
            @PathVariable("phoneNumber") String phoneNumber,
            @PathVariable("profile") String profile
    )
    {
//        System.out.println(productId + " " + quantity);
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String myUserDetailName = ((MyUserDetails) principal).getUsername();
//
//        User user = userService.getUserByEmail(myUserDetailName);
//        Cart cart = user.getCart();
        User user = userRepository.findById(3L).get();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setProfile(profile);
        User newUser = userRepository.save(user);
        return "ok";
    };

    @PostMapping("/delete/{productId}")
    public String delete(
            @PathVariable("productId") String productId)
    {
//        User user = userRepository.findById(2L).get();
//        Cart cart = user.getCart();
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String myUserDetailName = ((MyUserDetails) principal).getUsername();
//
//        User user = userService.getUserByEmail(myUserDetailName);
//        Cart cart = user.getCart();

        User user = userRepository.findById(3L).get();
        Cart cart = user.getCart();
        cartService.deleteCardItem(new CartItemPK(productId, cart.getCartId()));
        return productId;
    };

    @PostMapping("/update/{productId}/{quantity}")
    public String update(
            @PathVariable("productId") String productId,
            @PathVariable("quantity") int quantity)
    {
//        System.out.println(productId + " " + quantity);
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String myUserDetailName = ((MyUserDetails) principal).getUsername();
//
//        User user = userService.getUserByEmail(myUserDetailName);
//        Cart cart = user.getCart();
        User user = userRepository.findById(3L).get();
        Cart cart = user.getCart();
        double subtotal = cartService.updateQuantity(quantity,productId,cart.getCartId());
        return String.valueOf(subtotal);
    };
}
