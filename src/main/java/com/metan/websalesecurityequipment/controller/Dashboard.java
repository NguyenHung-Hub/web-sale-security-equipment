package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.Order;
import com.metan.websalesecurityequipment.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {

    private final OrderService orderService;

    public Dashboard(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String dashboard(Model model) {

        return "redirect:/dashboard/product";
    }
    @GetMapping("/product")
    public String product(Model model) {

        return "product_db";
    }

    @GetMapping("/brand")
    public String brand(Model model) {

        return "brand_db";
    }

    @GetMapping("/order")
    public String order(Model model) {

        List<Order> orders = orderService.findAll();
        System.out.println(orders.get(0).getContent());
        model.addAttribute("orders", orders);
        return "order_db";
    }


}
