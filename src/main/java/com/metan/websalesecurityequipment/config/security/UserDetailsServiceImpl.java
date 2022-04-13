package com.metan.websalesecurityequipment.config.security;

import com.metan.websalesecurityequipment.common.MyUserDetails;
import com.metan.websalesecurityequipment.model.User;
import com.metan.websalesecurityequipment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);

        if (user != null) {
            return new MyUserDetails(user);
        }
        throw new UsernameNotFoundException("Could not find user");
    }
}
