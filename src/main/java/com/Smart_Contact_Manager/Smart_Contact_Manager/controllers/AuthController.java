package com.Smart_Contact_Manager.Smart_Contact_Manager.controllers;


import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.Message;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.MessageType;
import com.Smart_Contact_Manager.Smart_Contact_Manager.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam("token") String token , HttpSession session
    ){
        User user =  userRepo.findByEmailToken(token).orElse(null);

        if(user != null){
            //user is Fetch
            if(user.getEmailToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message" , Message.builder()
                        .type(MessageType.green)
                        .content("Email is verified ! Now you can login")
                        .build());
                return "success_page";
            }
            session.setAttribute("message" , Message.builder()
                    .type(MessageType.red)
                    .content("Email not verified ! Token is invalid")
                    .build());
            return "error_page";

        }



        return "error_page";
    }
}
