package com.metan.websalesecurityequipment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {
    @GetMapping
    public String showMessage() {
        return "message";
    }
}
