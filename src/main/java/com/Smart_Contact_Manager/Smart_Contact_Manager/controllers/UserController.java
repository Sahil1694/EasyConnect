package com.Smart_Contact_Manager.Smart_Contact_Manager.controllers;

import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.Helper;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public  void addLoggedInUserInformation(Model model,Authentication authentication){
        String username = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("Username: " + username);

        System.out.println("Username: " + username);

        User user = userService.getUserByEmail(username);

        model.addAttribute("loggedInUser", user);
    }


    // user dashbaord page
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {
       String username = Helper.getEmailOfLoggedInUser(authentication);

       logger.info("Username: " + username);

       System.out.println("Username: " + username);

       User user = userService.getUserByEmail(username);

       model.addAttribute("loggedInUser", user);


        return "user/profile";
    }

    // user add contacts.js page

    // user view contacts.js

    // user edit contact

    // user delete contact

}