package com.metan.websalesecurityequipment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/add-product")
public class AddProductController {

    @GetMapping
    public String addProduct(){
        return "add_product";
    }
}
