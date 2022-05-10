package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.common.Utility;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.service.CartService;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/account")
public class LoginAndRegisterController {

    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public LoginAndRegisterController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
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

    @PostMapping("/register")
    public String processRegistration(User user, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        userService.registerUser(user);

        String siteUrl = Utility.getSiteUrl(request);
        userService.sendVerificationEmail(user, siteUrl);
        model.addAttribute("message", "Kiểm tra mail trong email " + user.getEmail());
        return "message";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model) {
        User userVerified = userService.getUserByVerificationCode(code);
        if (userVerified == null) {
            model.addAttribute("message", "Xác thực thất bại");
        } else {
            cartService.createCartNewUser(userVerified);
            model.addAttribute("message", "Xác thực thành công");
        }
        return "message";
    }
}
