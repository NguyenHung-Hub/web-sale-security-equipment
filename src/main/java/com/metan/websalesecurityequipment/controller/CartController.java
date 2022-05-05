package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.config.security.MyUserDetails;
import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.repository.CartRepository;
import com.metan.websalesecurityequipment.repository.UserRepository;
import com.metan.websalesecurityequipment.service.CartService;
import com.metan.websalesecurityequipment.service.OrderService;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public String showCart(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = user.getCart();
//        User user = userRepository.findById(2L).get();
//        Cart cart = user.getCart();
        model.addAttribute("CUSTOMER", user);

        if (cart.getCartItems().size() <= 0){
            model.addAttribute("LIST_ITEMCART",null);
        }else{
            model.addAttribute("LIST_ITEMCART",cart.getCartItems());
        }
        return "cart";
    };

    @PostMapping("/checkout")
    public String showCheckout(
            @RequestParam(value="selected") String selected,
            Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = user.getCart();
//        User user = userRepository.findById(2L).get();
//        Cart cart = user.getCart();
        if(selected==""){
            return "redirect:/cart";
        }
        List<String> listItemSelected = Arrays.asList(selected.split("/"));
        List<CartItem> cartItems = new ArrayList<CartItem>();
        cart.getCartItems().forEach(cartItem -> {
            if(listItemSelected.contains(cartItem.getProduct().getProductId())){
                cartItems.add(cartItem);
            };

        });
        model.addAttribute("CUSTOMER", user);
        model.addAttribute("LIST_ITEMCART",cartItems);
        return "checkout";
    };

    @PostMapping("/checkout/confirm")
    public String confirmCheckout(
            @RequestParam(value="selected") String selected) {
        System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = user.getCart();
//        User user = userRepository.findById(2L).get();
//        Cart cart = user.getCart();
        Order order = new Order("test 1");
        List<OrderItem> orderItems =  new ArrayList<>();

        order.setUser(user);
        order.setModifiedAt(new Date());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setContent("new");
        order.setTotal(0.0);
        order.setDueDate(new Date());
        List<String> listItemSelected= Arrays.asList(selected.split("/"));

        cart.getCartItems().forEach(cartItem -> {
            if(listItemSelected.contains(cartItem.getProduct().getProductId())){
                Product product = cartItem.getProduct();

                OrderItem orderItem = new OrderItem();
                orderItem.setCreatedAt(new Date());
                orderItem.setModifiedAt(new Date());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItems.add(orderItem);

                cartService.deleteCardItem(new CartItemPK(product.getProductId(), cart.getCartId()));
            };

        });

        order.setOrderItems(orderItems);
        orderService.save(order);

        return "redirect:/cart";
    };

    @GetMapping("/checkout")
    public String renderCart(){
        return "redirect:/cart";
    };
}
