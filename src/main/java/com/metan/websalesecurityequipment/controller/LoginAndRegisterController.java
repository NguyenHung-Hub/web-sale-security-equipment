package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class LoginAndRegisterController {

    private final UserService userService;

    @Autowired
    public LoginAndRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login-form";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();

        model.addAttribute("USER", user);
        return "signup-form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        userService.registerUser(user);
        return "redirect:/account/login";
    }
}
