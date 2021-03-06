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
            model.addAttribute("message", "Nh???p password");
            return "signup-form";
        } else if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", user.getPassword())){
            model.addAttribute("message", "T???i thi???u 8 k?? t???, ??t nh???t m???t ch??? c??i v?? m???t s???!");
            return "signup-form";
        } else if(rePassword.isBlank()) {
            model.addAttribute("message", "Nh???p l???i password!");
            return "signup-form";
        } else if(!user.getPassword().equals(rePassword)) {
            model.addAttribute("message", "Nh???p l???i password kh??ng tr??ng kh???p!");
            return "signup-form";
        } else if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Email ???? ???????c s??? d???ng!");
            return "signup-form";
        } else {
            String siteUrl = Utility.getSiteUrl(request);
            userService.sendVerificationEmail(userService.registerUser(user), siteUrl);
            model.addAttribute("title", "X??c th???c t??i kho???n c???a b???n");
            model.addAttribute("message", "Vui l??ng ki???m tra trong email c???a b???n. Ch??ng t??i ???? g???i cho b???n ?????n: <br><b>" + user.getEmail() + "</b></p>");
            return "message";
        }
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model) {
        User userVerified = userService.getUserByVerificationCode(code);
        if (userVerified == null) {
            model.addAttribute("title", "Th??ng b??o");
            model.addAttribute("message", "???? h???t th???i gian x??c th???c vui l??ng ????ng k?? l???i!");
        } else {
            cartService.createCartNewUser(userVerified);
            model.addAttribute("title", "Th??ng b??o");
            model.addAttribute("message", "<div style='text-center'>X??c th???c th??nh c??ng</div>");
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
            model.addAttribute("title", "X??c th???c t??i kho???n c???a b???n");
            model.addAttribute("message", "Vui l??ng ki???m tra trong email c???a b???n. Ch??ng t??i ???? g???i cho b???n ?????n: <br><b>" + user.getEmail() + "</b></p>");
            return "message";
        } else {
            model.addAttribute("message", "T??m ki???m kh??ng tr??? v??? k???t qu??? n??o. Vui l??ng th??? l???i v???i th??ng tin kh??c.");
            return "forgot-password";
        }
    }

    @GetMapping("/verify-forgot-password")
    public String verifyForgotPassword(@Param("code") String code, Model model) {
        User userVerified = userService.getUserByVerificationCodeForgotPassword(code);
        System.out.println(userVerified);
        if (userVerified == null) {
            model.addAttribute("title", "Th??ng b??o");
            model.addAttribute("message", "<div style='text-center'>X??c th???c th??nh c??ng</div>");
            return "message";
        } else {
            model.addAttribute("user", userVerified);
            return "verify-forgot-pass";
        }
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") User user, @Param("repassword") String rePassword, Model model) {
        if (user.getPassword().isBlank()) {
            model.addAttribute("message", "Nh???p password");
            return "verify-forgot-pass";
        } else if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", user.getPassword())){
            model.addAttribute("message", "T???i thi???u 8 k?? t???, ??t nh???t m???t ch??? c??i v?? m???t s???!");
            return "verify-forgot-pass";
        } else if(rePassword.isBlank()) {
            model.addAttribute("message", "Nh???p l???i password!");
            return "verify-forgot-pass";
        } else if(!user.getPassword().equals(rePassword)) {
            model.addAttribute("message", "Nh???p l???i password kh??ng tr??ng kh???p!");
            return "verify-forgot-pass";
        } else {
            userService.updateUser(user);
            model.addAttribute("title", "Th??ng b??o");
            model.addAttribute("message", "?????i m???t kh???u th??nh c??ng!");
            return "message";
        }
    }
}
