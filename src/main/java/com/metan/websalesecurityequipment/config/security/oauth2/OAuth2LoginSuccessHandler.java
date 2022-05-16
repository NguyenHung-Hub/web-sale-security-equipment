package com.metan.websalesecurityequipment.config.security.oauth2;

import com.metan.websalesecurityequipment.model.AuthenticationProvider;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        User user = userService.getUserByEmail(email);

        String name = oAuth2User.getName();
        String clientName = oAuth2User.getClientName();
        if (user == null) {
            // register as new user
            if (clientName.equals(AuthenticationProvider.GOOGLE.getClientName()))
                userService.createNewUserAfterOAuthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);
            else if (clientName.equals(AuthenticationProvider.FACEBOOK.getClientName()))
                userService.createNewUserAfterOAuthLoginSuccess(email, name, AuthenticationProvider.GOOGLE);
        } else {
            // update existing user
            if (clientName.equals(AuthenticationProvider.GOOGLE.getClientName()))
                userService.updateUserAfterOAuthLoginSuccess(user, name, AuthenticationProvider.GOOGLE);
            else if (clientName.equals(AuthenticationProvider.FACEBOOK.getClientName()))
                userService.updateUserAfterOAuthLoginSuccess(user, name, AuthenticationProvider.FACEBOOK);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
