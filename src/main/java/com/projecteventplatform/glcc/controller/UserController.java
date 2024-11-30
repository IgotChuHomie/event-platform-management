package com.projecteventplatform.glcc.controller;

import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.service.EmailService;
import com.projecteventplatform.glcc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService ;

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user , @RequestParam("role") String role ) {
        user.setEnabled(false);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User createdUser = userService.createUser(user);
        userService.assignRoleToUser(createdUser.getEmail(), role);

        //email confirmation code
        String confirmationUrl = "http://localhost:8080/users/confirm?email=" + createdUser.getEmail();
        String emailContent = "<p>Thank you for registering. Please click the link below to confirm your account:</p>" +
                "<a href=\"" + confirmationUrl + "\">Confirm Your Account</a>";

        try {
            emailService.sendConfirmationEmail(createdUser.getEmail(), "Confirm Your Account", emailContent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send confirmation email");
        }
        return "redirect:/events";
    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);

        if (user != null && !user.isEnabled()) {
            user.setEnabled(true);
            userService.createUser(user);
            return "redirect:/login?confirmed";
        }

        return "redirect:/login?error";
    }
}
