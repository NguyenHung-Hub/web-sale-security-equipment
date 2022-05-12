package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.common.Utility;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.service.CartService;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

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

        model.addAttribute("user", user);
        return "signup-form";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") User user, BindingResult result,@Param("rePassword") String rePassword, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        System.out.println(user.getPassword());
        if (result.hasErrors()) {
            return "signup-form";
        } else if (user.getPassword().isBlank()) {
            model.addAttribute("message", "Nhập password");
            return "signup-form";
        } else if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", user.getPassword())){
            model.addAttribute("message", "Tối thiểu 8 ký tự, ít nhất một chữ cái và một số!");
            return "signup-form";
        } else if(rePassword.isBlank()) {
            model.addAttribute("message", "Nhập lại password!");
            return "signup-form";
        } else if(!user.getPassword().equals(rePassword)) {
            model.addAttribute("message", "Nhập lại password không trùng khớp!");
            return "signup-form";
        } else if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Email đã được sử dụng!");
            return "signup-form";
        } else {
            String siteUrl = Utility.getSiteUrl(request);
            userService.sendVerificationEmail(userService.registerUser(user), siteUrl);
            model.addAttribute("message", "Kiểm tra mail trong email " + user.getEmail());
            return "message";
        }
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model) {
        User userVerified = userService.getUserByVerificationCode(code);
        if (userVerified == null) {
            model.addAttribute("message", "Đã hết thời gian xác thực vui lòng đăng ký lại!");
        } else {
            cartService.createCartNewUser(userVerified);
            model.addAttribute("message", "Xác thực thành công");
        }
        return "message";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@Param("email") String email, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            String siteUrl = Utility.getSiteUrl(request);
            userService.sendVerificationEmailForgotPassword(user, siteUrl);
            model.addAttribute("message", "Kiểm tra mail trong email " + user.getEmail());
            return "message";
        } else {
            model.addAttribute("message", "Không tìm thấy người dùng này!");
            return "forgot-password";
        }
    }

    @GetMapping("/verify-forgot-password")
    public String verifyForgotPassword(@Param("code") String code, Model model) {
        User userVerified = userService.getUserByVerificationCodeForgotPassword(code);
        System.out.println(userVerified);
        if (userVerified == null) {
            model.addAttribute("message", "Xác thực thất bại");
            return "message";
        } else {
            model.addAttribute("user", userVerified);
            return "verify-forgot-pass";
        }
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") User user, @Param("repassword") String rePassword, Model model) {
        if (user.getPassword().isBlank()) {
            model.addAttribute("message", "Nhập password");
            return "verify-forgot-pass";
        } else if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", user.getPassword())){
            model.addAttribute("message", "Tối thiểu 8 ký tự, ít nhất một chữ cái và một số!");
            return "verify-forgot-pass";
        } else if(rePassword.isBlank()) {
            model.addAttribute("message", "Nhập lại password!");
            return "verify-forgot-pass";
        } else if(!user.getPassword().equals(rePassword)) {
            model.addAttribute("message", "Nhập lại password không trùng khớp!");
            return "verify-forgot-pass";
        } else {
            userService.updateUser(user);
            model.addAttribute("message", "Đổi mật khẩu thành công!");
            return "message";
        }
    }
}
