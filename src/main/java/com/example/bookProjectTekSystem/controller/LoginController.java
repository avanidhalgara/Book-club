package com.example.bookProjectTekSystem.controller;

import com.example.bookProjectTekSystem.context.GlobalContext;
import com.example.bookProjectTekSystem.helper.Message;
import com.example.bookProjectTekSystem.model.Role;
import com.example.bookProjectTekSystem.model.User;
import com.example.bookProjectTekSystem.repository.RoleRepository;
import com.example.bookProjectTekSystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller

public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String login() {
        GlobalContext.cart.clear();

        return "login";
    }


    @GetMapping("/register")
    public String registerGet() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request, Model model, HttpSession session) throws ServletException {

        try {
            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(password));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user);
            request.login(user.getEmail(), password);
            session.setAttribute("message", new Message("Successfully registered!!", "alert-success"));
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Something went Wrong!!", e.getMessage());
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went Wrong!!" + e.getMessage(), "alert-danger"));
            return "register";
        }

    }


}
