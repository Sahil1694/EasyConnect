package com.Smart_Contact_Manager.Smart_Contact_Manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "EasyConnect");
        model.addAttribute("Github", "https://github.com/Sahil1694");
        return "home";
    }

    // About Page
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String aboutPage() {
        return "about";
    }

    // Services Page
    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String servicesPage() {
        return "services";
    }

    //Contact Page
    @GetMapping("/contact")
    public String contactpage(){
        return "contact";
    }
    //Login
    @GetMapping("/login")
    public String Loginpage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerpage(){
        return "register";
    }
}
