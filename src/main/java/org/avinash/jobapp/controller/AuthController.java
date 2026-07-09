package org.avinash.jobapp.controller;

import org.avinash.jobapp.dto.RegistrationDto;
import org.avinash.jobapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("registration")) {
            model.addAttribute("registration", new RegistrationDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationDto registration, RedirectAttributes redirectAttributes) {
        try {
            userService.register(registration);
            redirectAttributes.addFlashAttribute("success", "Account created! Please sign in.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("registration", registration);
            return "redirect:/register";
        }
    }
}
