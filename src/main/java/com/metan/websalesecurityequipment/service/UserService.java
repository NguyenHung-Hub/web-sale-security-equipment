package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.AuthenticationProvider;
import com.metan.websalesecurityequipment.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    public User registerUser(User user);
    public void sendVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException;
    public void sendVerificationEmailForgotPassword(User user, String siteUrl)  throws MessagingException, UnsupportedEncodingException;
    public User getUserByVerificationCode(String code);
    public User getUserByVerificationCodeForgotPassword(String code);
    public void updateUser(User user);
    public User getUserByEmail(String email);
    public void createNewUserAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider authProvider);
    public void updateUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider authProvider);
    public List<User> findAll();
}
