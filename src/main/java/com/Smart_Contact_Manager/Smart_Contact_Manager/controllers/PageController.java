package com.Smart_Contact_Manager.Smart_Contact_Manager.controllers;

import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import com.Smart_Contact_Manager.Smart_Contact_Manager.forms.UserForm;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.Message;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.MessageType;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }


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
    public String registerpage( Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm",userForm);

        return "register";
    }

    //processing Register form
    @RequestMapping( value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm , BindingResult bindingResult,  HttpSession session){
        System.out.println("Register form submitted");
        //fetching data
        System.out.println(userForm);

        //validating user data
        if(bindingResult.hasErrors()){
            System.out.println("Error");
            System.out.println(bindingResult);
            return "register";
        }


        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("https://moonvillageassociation.org/wp-content/uploads/2018/06/default-profile-picture1.jpg");
        User saveUser = userService.SaveUser(user);

        System.out.println(saveUser);


        //save data to database
        //message ="Registration Successfull"
        //add messsage

        Message message = Message.builder().content("Registration Successfull").type(MessageType.green).build();

        session.setAttribute("message",message);

        //redirect to login page
        return "redirect:/register";
    }



    

}
