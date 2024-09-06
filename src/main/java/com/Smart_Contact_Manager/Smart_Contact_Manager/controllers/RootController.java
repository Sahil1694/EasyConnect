package com.Smart_Contact_Manager.Smart_Contact_Manager.controllers;


import ch.qos.logback.classic.Logger;
import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.Helper;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserService userService;

    @ModelAttribute
    public  void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication == null){
            return;
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("Username: " + username);

        System.out.println("Username: " + username);

        User user = userService.getUserByEmail(username);


        model.addAttribute("loggedInUser", user);
    }
}
