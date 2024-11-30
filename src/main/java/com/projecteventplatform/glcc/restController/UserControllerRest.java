package com.projecteventplatform.glcc.restController ;

import com.projecteventplatform.glcc.entities.Role;
import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserControllerRest {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = userService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }


    @PostMapping("/{email}/roles")
    public ResponseEntity<String> assignRoleToUser(@PathVariable String email, @RequestParam String roleName) {
        userService.assignRoleToUser(email, roleName);
        return ResponseEntity.ok("Role " + roleName + " assigned to user " + email);
    }
}
