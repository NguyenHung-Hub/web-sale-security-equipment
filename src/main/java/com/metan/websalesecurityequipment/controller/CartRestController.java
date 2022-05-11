package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.config.security.MyUserDetails;
import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.repository.UserRepository;
import com.metan.websalesecurityequipment.service.CartService;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = user.getCart();;
//        User user = userRepository.findById(3L).get();
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = user.getCart();

//        User user = userRepository.findById(3L).get();
//        Cart cart = user.getCart();
        cartService.deleteCardItem(new CartItemPK(productId, cart.getCartId()));
        return productId;
    };

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
//        User user = userRepository.findById(3L).get();
//        Cart cart = user.getCart();
        double subtotal = cartService.updateQuantity(quantity,productId,cart.getCartId());
        return String.valueOf(subtotal);
    };

    @GetMapping("/order/{type}")
    public List<Order> getOrderStatus(
            @PathVariable("type") String type)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        List<Order> Oders = user.getOrders();
        List<Order> NewOders = new ArrayList<>();
        for(int i = 0 ;i< Oders.size();i++){
            if (Oders.get(i).getOrderStatus().getStatus() == type.toLowerCase()){
                NewOders.add(Oders.get(i));
            }
        }
        return NewOders;
    };
}
