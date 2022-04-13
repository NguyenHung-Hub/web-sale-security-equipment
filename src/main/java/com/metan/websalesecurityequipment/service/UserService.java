package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.AuthenticationProvider;
import com.metan.websalesecurityequipment.model.User;

public interface UserService {
    public void registerUser(User user);
    public void updateUser(User user);
    public User getUserByEmail(String email);
    public void createNewUserAfterOAuthLoginSuccess(String email, String name, AuthenticationProvider authProvider);
}
