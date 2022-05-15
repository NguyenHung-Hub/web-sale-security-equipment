package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.config.security.MyUserDetails;
import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.repository.CartRepository;
import com.metan.websalesecurityequipment.repository.UserRepository;
import com.metan.websalesecurityequipment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private EmailSenderService emailSenderService;


    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public String showCart(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
//        Cart cart = cartService.findByUser(user.getCart().getCartId());
//        User user = userRepository.findById(3L).get();

        List<Category> categories = null;
        model.addAttribute("CATEGORIES", categories);
        Cart cart = user.getCart();
        model.addAttribute("CUSTOMER", user);

        if (cart.getCartItems().size() <= 0){
            model.addAttribute("LIST_ITEMCART",null);
        }else{
            model.addAttribute("LIST_ITEMCART",cart.getCartItems());
        }
        return "cart";
    };

    @GetMapping("/order")
    public String showOrder(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);

        List<Category> categories = null;
        model.addAttribute("CATEGORIES", categories);
        Cart cart = user.getCart();
        model.addAttribute("CUSTOMER", user);

        if (cart.getCartItems().size() <= 0){
            model.addAttribute("LIST_ITEMCART",null);
        }else{
            model.addAttribute("LIST_ITEMCART",cart.getCartItems());
        }
        return "orders";
    };

    @GetMapping("/order/{type}")
    public String callApiForStatusOrders(@PathVariable("type") String type,Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        List<Category> categories = null;
        model.addAttribute("CATEGORIES", categories);

//        final String uri = "http://localhost:8080/api/cart/order/"+type;
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Order[]> responseEntity = restTemplate.getForEntity(uri, Order[].class);
//        Order[] orders = responseEntity.getBody();

        List<Order> Oders = user.getOrders();
        List<Order> NewOders = new ArrayList<>();
        for(int i = 0 ;i< Oders.size();i++){
            if(Oders.get(i).getOrderStatus().getStatus().equalsIgnoreCase(type)){
                NewOders.add(Oders.get(i));
            }
        }

        model.addAttribute("CUSTOMER", user);

        if(!type.equalsIgnoreCase("PROCESSING")){
            model.addAttribute("type",null);
        }

        if (NewOders.size() <= 0){
            model.addAttribute("LIST_ORDER",null);
        }else{
            model.addAttribute("LIST_ORDER",NewOders);
        }
        return "orders";
    };

    @PostMapping("/checkout")
    public String showCheckout(
            @RequestParam(value="selected") String selected,
            Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = cartService.findByUser(user.getUserId());
//        User user = userRepository.findById(3L).get();
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
            @RequestParam(value="selected") String selected,
            @RequestParam(value="totalOrder") double total,
            @RequestParam(value="dueDate") String dueDate,
            @RequestParam(value="content") String content
    ) throws ParseException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String myUserDetailName = ((MyUserDetails) principal).getUsername();

        User user = userService.getUserByEmail(myUserDetailName);
        Cart cart = cartService.findByUser(user.getUserId());
//        User user = userRepository.findById(3L).get();
//        Cart cart = user.getCart();
        String body ="Đơn hàng của bạn bao gồm : \n";

        String id = orderService.getLastId();
        System.out.println(id);
        Order order = new Order(id);
        List<OrderItem> orderItems =  new ArrayList<>();

        order.setUser(user);
        order.setModifiedAt(new Date());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setContent(content);
        order.setDueDate(new SimpleDateFormat("MM/dd/yyyy").parse(dueDate));
        List<String> listItemSelected= Arrays.asList(selected.split("/"));

        for (int i=0; i<cart.getCartItems().size();i++){
            CartItem cartItem = cart.getCartItems().get(i);
            if(listItemSelected.contains(cartItem.getProduct().getProductId())){
                Product product = cartItem.getProduct();

                OrderItem orderItem = new OrderItem();
                orderItem.setCreatedAt(new Date());
                orderItem.setModifiedAt(new Date());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItems.add(orderItem);

                body += (String.valueOf(orderItem.getQuantity()) + " " + orderItem.getProduct().getTitle() +"\n");

                cartService.deleteCardItem(new CartItemPK(product.getProductId(), cart.getCartId()));
            };
        }
//        cart.getCartItems().forEach(cartItem -> {
//            if(listItemSelected.contains(cartItem.getProduct().getProductId())){
//                Product product = cartItem.getProduct();
//
//                OrderItem orderItem = new OrderItem();
//                orderItem.setCreatedAt(new Date());
//                orderItem.setModifiedAt(new Date());
//                orderItem.setQuantity(cartItem.getQuantity());
//                orderItem.setProduct(product);
//                orderItem.setOrder(order);
//                orderItems.add(orderItem);
//
//                body += (String.valueOf(orderItem.getQuantity()) + " " + orderItem.getProduct().getTitle() +"\n");
//
//                cartService.deleteCardItem(new CartItemPK(product.getProductId(), cart.getCartId()));
//            };
//
//        });

        order.setOrderItems(orderItems);
        order.setTotal(total);
        orderService.save(order);

        System.out.println(body);
        emailSenderService.sendEmail(
                user.getEmail(),
                "Đơn hàng " + order.getOrderId() + " đã được đặt",
                body
        );

        return "redirect:/cart";
    };

    @GetMapping("/checkout")
    public String renderCart(){
        return "redirect:/cart";
    };
}
