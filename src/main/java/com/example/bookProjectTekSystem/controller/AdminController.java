package com.example.bookProjectTekSystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


//  control all the admin related routes
@Controller
public class AdminController {

    //  Get the admin routes and return adminHome.html  page
    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }
}
