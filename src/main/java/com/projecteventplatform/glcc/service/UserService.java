package com.projecteventplatform.glcc.service;

import com.projecteventplatform.glcc.entities.Role;
import com.projecteventplatform.glcc.entities.User;

public interface UserService {
    User createUser(User user);
    Role createRole(Role role);
    void assignRoleToUser(String email, String roleName);
    User getUserByEmail(String email);
}