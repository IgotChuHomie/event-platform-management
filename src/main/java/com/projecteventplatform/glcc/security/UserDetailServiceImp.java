package com.projecteventplatform.glcc.security;

import com.projecteventplatform.glcc.entities.Role;
import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {
    private final UserService userService ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user  = userService.getUserByEmail(email) ;
        if (user == null) throw new RuntimeException("this user doesn't exist") ;
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getRole).toArray(String[]::new))
                .build() ;
    }
}
