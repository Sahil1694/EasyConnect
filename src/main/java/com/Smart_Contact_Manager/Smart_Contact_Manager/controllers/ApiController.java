package com.Smart_Contact_Manager.Smart_Contact_Manager.controllers;


import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.Contact;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId) {

        return contactService.getById(contactId);
    }

}
