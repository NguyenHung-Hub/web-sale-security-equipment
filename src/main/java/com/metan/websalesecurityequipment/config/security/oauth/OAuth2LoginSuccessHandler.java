package com.metan.websalesecurityequipment.config.security.oauth;

import com.metan.websalesecurityequipment.model.AuthenticationProvider;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        User user = userService.getUserByEmail(email);

        String name = oAuth2User.getName();
        if (user == null) {
            // register as new user
            userService.createNewUserAfterOAuthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);
        } else {
            // update existing user
            userService.updateUserAfterOAuthLoginSuccess(user, name, AuthenticationProvider.GOOGLE);
        }

        System.out.println("User's email " + email);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
