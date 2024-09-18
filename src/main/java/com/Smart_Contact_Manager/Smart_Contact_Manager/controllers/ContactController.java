package com.Smart_Contact_Manager.Smart_Contact_Manager.controllers;


import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.Contact;
import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import com.Smart_Contact_Manager.Smart_Contact_Manager.forms.ContactForm;
import com.Smart_Contact_Manager.Smart_Contact_Manager.forms.ContactSearchForm;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.AppConstants;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.Helper;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.Message;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.MessageType;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.ContactService;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.ImageService;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    UserService userService;


    //Add Contact page
    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", new ContactForm());
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {


        // Get the username of the logged-in user
        String username = Helper.getEmailOfLoggedInUser(authentication);

        if (result.hasErrors()) {
            // If validation errors occur, send an error message to the session
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        // Retrieve the user by email
        User user = userService.getUserByEmail(username);

        // Create a new contact and set its details
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }
        contactService.save(contact);
        System.out.println(contactForm);

        // Send a success message to the session
        session.setAttribute("message", Message.builder()
                .content("You have successfully added a new contact")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/add";    }

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value ="size", defaultValue =AppConstants.PAGE_SIZE +"" ) int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model , Authentication authentication){

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);


        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    //search handler
    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication
    ) {

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction , user);
        }else if(contactSearchForm.getField().equalsIgnoreCase("Email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
        }

        logger.info("Page Contact: " + pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContact", pageContact);

        return "user/search";

    }

    //delete Contact

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session
    ){
        contactService.delete(contactId);

        session.setAttribute("message", Message.builder()
                .content("You have successfully deleted the contact")
                .type(MessageType.green)
                .build());


        return "redirect:/user/contacts";
    }

    //Update Contact

    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {

        var contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        ;
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
                                @Valid @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult,
                                Model model) {

        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

        return "redirect:/user/contacts/view/" + contactId;
    }
}
