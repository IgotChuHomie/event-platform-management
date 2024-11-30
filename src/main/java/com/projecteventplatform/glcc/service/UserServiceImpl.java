package com.projecteventplatform.glcc.service;

import com.projecteventplatform.glcc.entities.Role;
import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.repositories.RoleRepository;
import com.projecteventplatform.glcc.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = userRepository.findUserByEmail(email) ;
        if (user == null ) throw new RuntimeException("there is no user with this email "+email) ;
        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email) ;
        if (user == null ) throw new RuntimeException("there is no user with this email "+email) ;
        return user ;
    }
}
