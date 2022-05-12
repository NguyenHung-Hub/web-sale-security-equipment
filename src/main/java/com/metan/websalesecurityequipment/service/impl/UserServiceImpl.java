package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.AuthenticationProvider;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.repository.CartRepository;
import com.metan.websalesecurityequipment.repository.UserRepository;
import com.metan.websalesecurityequipment.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public User registerUser(User user) {
        encodePassword(user);
        user.setRegisteredAt(new Date());
        user.setRole("USER");
        user.setAuthProvider(AuthenticationProvider.LOCAL);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        return userRepository.save(user);
    }

    @Override
    public void sendVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException {

        String subject = "Please verify your registration";
        String senderName = "Metan team";
        String mailContent = "<p>Dear " + user.getFullName() + ",</p>";
        mailContent += "<p>Please click the link below to vefify to your registration:</p>";

        String verifyUrl = siteUrl + "/account/verify?code=" + user.getVerificationCode();
        mailContent += "<h3><a href='" + verifyUrl +"'>VERIFY</a></h3>";
        mailContent += "<p>Thank you<br>The Metan Team</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("dohuyhoang.iuh.k15@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);

//        userRepository.registerEvent(user.getVerificationCode(), user.getEmail());
    }

    @Override
    public void sendVerificationEmailForgotPassword(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        user.setVerificationCode(randomCode);

        userRepository.updateVerificationCode(user.getVerificationCode(), user.getUserId());

        String subject = "Please verify your registration";
        String senderName = "Metan team";
        String mailContent = "<p>Dear " + user.getFullName() + ",</p>";
        mailContent += "<p>Please click the link below to vefify to your forgot password:</p>";

        String verifyUrl = siteUrl + "/account/verify-forgot-password?code=" + user.getVerificationCode();
        mailContent += "<h3><a href='" + verifyUrl +"'>VERIFY</a></h3>";
        mailContent += "<p>Thank you<br>The Metan Team</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("dohuyhoang.iuh.k15@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }

    @Override
    public User getUserByVerificationCode(String code) {
        User user = userRepository.findByVerificationCode(code);

        if (user == null || user.isEnable()) {
            return null;
        } else {
            userRepository.enable(user.getUserId());
            return user;
        }
    }

    @Override
    public User getUserByVerificationCodeForgotPassword(String code) {
        return userRepository.findByVerificationCode(code);
    }


    @Override
    public void updateUser(User user) {
        encodePassword(user);
        userRepository.updatePassword(user.getUserId(), user.getPassword());
    }

    @Override
    public User getUserByEmail(String email) {
       return userRepository.getUserByEmail(email);
    }

    @Override
    public void createNewUserAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider authProvider) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(name);
        user.setRegisteredAt(new Date());
        user.setAuthProvider(authProvider);
        user.setRole("USER");

        userRepository.save(user);
    }

    @Override
    public void updateUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider authProvider) {
        user.setFirstName(name);
        user.setAuthProvider(authProvider);

        userRepository.save(user);
    }

    private void encodePassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));
    }
}
